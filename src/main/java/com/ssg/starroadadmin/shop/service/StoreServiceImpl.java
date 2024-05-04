package com.ssg.starroadadmin.shop.service;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.shop.dto.SearchStoreRequest;
import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.dto.StoreRegisterRequest;
import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import com.ssg.starroadadmin.global.error.exception.ShopException;
import com.ssg.starroadadmin.shop.repository.ComplexShoppingmallRepository;
import com.ssg.starroadadmin.shop.repository.StoreRepository;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
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
    public void createStore(Long mallManagerId, StoreRegisterRequest request) {
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
        storeRepository.save(store);
    }

    @Override
    public Store getStore(long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));
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
    public Page<StoreListResponse> searchStoreList(Long mallManagerId, SearchStoreRequest request) {
        // 매장 관리자 정보 가져오기
        managerRepository.findByIdAndAuthorityNot(mallManagerId, Authority.STORE)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        Pageable pageable = PageRequest.of(request.pageNumber(), request.pageSize(), Sort.by(request.direction(), "name"));
        Page<StoreListResponse> byNameContainingOrFloorOrStoreType = storeRepository.findByNameContainingOrFloorOrStoreType(
                request.storeName(), request.floor(), request.storeType(), pageable
        );

        return byNameContainingOrFloorOrStoreType;
    }
}
