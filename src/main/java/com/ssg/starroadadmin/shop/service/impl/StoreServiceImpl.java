package com.ssg.starroadadmin.shop.service.impl;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.ReviewErrorCode;
import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.ReviewException;
import com.ssg.starroadadmin.global.error.exception.ShopException;
import com.ssg.starroadadmin.global.util.S3Uploader;
import com.ssg.starroadadmin.review.enums.ConfidenceType;
import com.ssg.starroadadmin.shop.dto.*;
import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.repository.ComplexShoppingmallRepository;
import com.ssg.starroadadmin.shop.repository.StoreRepository;
import com.ssg.starroadadmin.shop.repository.StoreRepositoryCustom;
import com.ssg.starroadadmin.shop.service.StoreService;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final S3Uploader s3Uploader;

    private final StoreRepository storeRepository;
    private final StoreRepositoryCustom storeRepositoryCustom;
    private final ComplexShoppingmallRepository complexShoppingmallRepository;
    private final ManagerRepository managerRepository;

    /**
     * 로그인 한 관리자의 ID(PK)를 바탕으로 권한 확인
     * StoreRegisterRequest를 통해 매장 정보를 받아와서 Store에 정보 기입
     * Store.CompleteShoppingmall에 관리자를 통해서 쇼핑몰 정보를 가져와 기입
     *
     * @param email
     * @param request
     */
    @Transactional
    public Long createStore(String email, StoreCreateRequest request,Long storeManagerId) {

        // 쇼핑몰 관리자 정보 가져오기
        Manager mallManager = managerRepository.findByUsernameAndAuthority(email, Authority.MALL)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        //관리자를 추가를 한 후에
        // 매장 관리자 정보 가져오기 , jwt로 받아온
        Manager storeManager = managerRepository.findById(storeManagerId).orElseThrow(()->new ManagerException(ManagerErrorCode.STORE_MANAGER_NOT_FOUND));
        ComplexShoppingmall shoppingmall = complexShoppingmallRepository.findByManagerId(mallManager.getId())
                .orElseThrow(() -> new ShopException(ShopErrorCode.SHOPPINGMALL_NOT_FOUND));


        String dirName = "ssg/mall/" + shoppingmall.getName() + "/store/location";
        String imagePath = s3Uploader.upload(request.createStoreGuideMap(), dirName);

        Store store = Store.builder()
                .name(request.createStoreName())
                .floor(request.createStoreFloor())
                .storeType(request.createStoreType())
                .complexShoppingmall(shoppingmall)
                .manager(storeManager)
                .storeGuideMap(imagePath)
                .build();

        // 매장 추가
        Store saveStore = storeRepository.save(store);

        return saveStore.getId();
    }

    /**
     * 로그인한 ID(PK)를 바탕으로 권한 확인
     * SearchStoreRequest를 통해 가져온 정보들을 바탕으로
     * 조건에 매장 목록들을 검색
     *
     * @param email
     * @param request
     * @return
     */
    @Transactional
    public Page<StoreListResponse> searchStoreList(String email, SearchStoreRequest request, Pageable pageable) {
        // 매장 관리자 정보 가져오기
        Manager manager = managerRepository.findByUsernameAndAuthority(email, Authority.MALL)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        // 복합 쇼핑몰 정보 가져오기
        ComplexShoppingmall shoppingmall = complexShoppingmallRepository.findByManagerId(manager.getId())
                .orElseThrow(() -> new ShopException(ShopErrorCode.SHOPPINGMALL_NOT_FOUND));

        Page<StoreListResponse> page = storeRepositoryCustom.findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
                shoppingmall.getId(), request.storeName(), request.floor(), request.storeType(), request.sortType(), pageable
        );

        return page;
    }

    /**
     * 매장 ID를 통해서 매장 상세정보를 가져옴
     * 매장 관리자의 본인 매장의 정보만 가져올 수 있음
     *
     * @param storeId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public StoreResponse getStore(String email, Long storeId) {
        // 매장 관리자 정보 가져오기
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Store store = null;
        // 매장 관리자인 경우 본인 매장 정보만 볼 수 있음
        if (manager.getAuthority() == Authority.STORE) {
            store = storeRepository.findByIdAndManagerId(storeId, manager.getId())
                    .orElseThrow(() -> new ShopException(ShopErrorCode.ACCESS_DENIED));
        } else {
            store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));
        }

        return StoreResponse.from(manager, store);
    }

    /**
     * 매장 ID를 통해서 매장 정보를 수정
     * StoreModifyRequest를 통해 가져온 정보를 바탕으로
     * 매장 관리자의 본인 매장의
     * 매장 설명, 층수, 운영시간, 연락처를 수정
     *
     * @param email
     * @param storeId
     * @param request
     */
    @Transactional
    @Override
    public void updateStore(String email, Long storeId, StoreModifyRequest request) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Store store = null;
        if (manager.getAuthority() == Authority.STORE) {
            store = storeRepository.findByIdAndManagerId(storeId, manager.getId())
                    .orElseThrow(() -> new ShopException(ShopErrorCode.ACCESS_DENIED));
        } else {
            store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));
        }

        store.updateInfo(request.contents(), request.operatingTime(), request.contactNumber());
    }

    /**
     * 매장 이미지 수정
     *
     * @param email
     * @param storeId
     * @param file
     */
    @Transactional
    @Override
    public void updateStoreImage(String email, Long storeId, MultipartFile file) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Store store = null;
        if (manager.getAuthority() == Authority.STORE) {
            store = storeRepository.findByIdAndManagerId(storeId, manager.getId())
                    .orElseThrow(() -> new ShopException(ShopErrorCode.ACCESS_DENIED));
        } else {
            store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));
        }

        String mallName = store.getComplexShoppingmall().getName();
        String dirName = "ssg/mall/" + mallName + "/store/logo";
        String imagePath = s3Uploader.upload(file, dirName);

        store.updateImagePath(imagePath);
    }

    /**
     * 매장 삭제
     *
     * @param email
     * @param storeId
     */
    @Transactional
    @Override
    public void deleteStore(String email, Long storeId) {
        Manager manager = managerRepository.findByUsernameAndAuthority(email, Authority.STORE)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        Store store = storeRepository.findByIdAndManagerId(storeId, manager.getId())
                .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));

        storeRepository.delete(store);
    }

    @Override
    public StoreConfidenceResponse getStoreConfidenceColor(String email, Long storeId) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));

        // 매장 관리자인 경우 본인 매장 정보만 볼 수 있음
        if (manager.getAuthority() == Authority.STORE && store.getManager().getId() != manager.getId()) {
            throw new ShopException(ShopErrorCode.ACCESS_DENIED);
        } else if (manager.getAuthority() == Authority.MALL && store.getComplexShoppingmall().getManager().getId() != manager.getId()) {
            throw new ShopException(ShopErrorCode.ACCESS_DENIED);
        }

        ConfidenceType storeConfidence = storeRepositoryCustom.findStoreConfidence(storeId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
        return new StoreConfidenceResponse(storeConfidence.name());
    }


}
