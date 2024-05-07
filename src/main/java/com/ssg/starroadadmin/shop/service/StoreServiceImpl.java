package com.ssg.starroadadmin.shop.service;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.ShopException;
import com.ssg.starroadadmin.shop.dto.SearchStoreRequest;
import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.dto.StoreModifyRequest;
import com.ssg.starroadadmin.shop.dto.StoreRegisterRequest;
import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.repository.ComplexShoppingmallRepository;
import com.ssg.starroadadmin.shop.repository.StoreRepository;
import com.ssg.starroadadmin.shop.repository.StoreRepositoryCustom;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreRepositoryCustom storeRepositoryCustom;
    private final ComplexShoppingmallRepository complexShoppingmallRepository;
    private final ManagerRepository managerRepository;

    /**
     * 로그인 한 관리자의 ID(PK)를 바탕으로 권한 확인
     * StoreRegisterRequest를 통해 매장 정보를 받아와서 Store에 정보 기입
     * Store.CompleteShoppingmall에 관리자를 통해서 쇼핑몰 정보를 가져와 기입
     *
     * @param mallManagerId
     * @param request
     */
    @Transactional
    public Long createStore(Long mallManagerId, StoreRegisterRequest request) {
        // 쇼핑몰 관리자 정보 가져오기
        Manager mallManager = managerRepository.findByIdAndAuthority(mallManagerId, Authority.MALL)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        // 매장 관리자 정보 가져오기
        Manager storeManager = managerRepository.findById(request.StoreManagerId())
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.STORE_MANAGER_NOT_FOUND));

        // 쇼핑몰 정보 가져오기
        ComplexShoppingmall shoppingmall = complexShoppingmallRepository.findByManagerId(mallManagerId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.SHOPPINGMALL_NOT_FOUND));

        Store store = Store.builder()
                .name(request.storeName())
                .floor(request.storeFloor())
                .storeType(request.storeType())
                .complexShoppingmall(shoppingmall)
                .manager(storeManager)
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
     * @param mallManagerId
     * @param request
     * @return
     */
    @Transactional
    public Page<StoreListResponse> searchStoreList(Long mallManagerId, SearchStoreRequest request) {
        // 매장 관리자 정보 가져오기
        managerRepository.findByIdAndAuthorityNot(mallManagerId, Authority.STORE)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        // 복합 쇼핑몰 정보 가져오기
        ComplexShoppingmall shoppingmall = complexShoppingmallRepository.findByManagerId(mallManagerId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.SHOPPINGMALL_NOT_FOUND));

        Pageable pageable = PageRequest.of(request.pageNumber(), request.pageSize());
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
    public Store getStore(Long managerId, Long storeId) {
        return storeRepository.findByIdAndManagerId(storeId, managerId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));
    }

    /**
     * 매장 ID를 통해서 매장 정보를 수정
     * StoreModifyRequest를 통해 가져온 정보를 바탕으로
     * 매장 관리자의 본인 매장의
     * 매장 설명, 층수, 운영시간, 연락처를 수정
     *
     * TODO : 매장 정보 수정을 매장 관리자 뿐만 아니라 쇼핑몰 관리자도 수정할 수 있도록 변경
     *
     * @param storeId
     * @param request
     */
    @Transactional
    @Override
    public void updateStore(Long managerId, Long storeId, StoreModifyRequest request) {
        managerRepository.findByIdAndAuthority(managerId, Authority.STORE)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        Store store = storeRepository.findByIdAndManagerId(storeId, managerId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));

        store.updateInfo(request.contents(), request.operatingTime(), request.contactNumber());
    }

    /**
     * 매장 이미지 수정
     *
     * @param managerId
     * @param storeId
     * @param imagePath
     */
    @Transactional
    @Override
    public void updateStoreImage(Long managerId, Long storeId, String imagePath) {
        managerRepository.findByIdAndAuthority(managerId, Authority.STORE)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        Store store = storeRepository.findByIdAndManagerId(storeId, managerId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));

        store.updateImagePath(imagePath);
    }


}
