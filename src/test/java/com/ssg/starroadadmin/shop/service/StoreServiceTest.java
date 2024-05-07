package com.ssg.starroadadmin.shop.service;

import com.ssg.starroadadmin.shop.dto.SearchStoreRequest;
import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.dto.StoreRegisterRequest;
import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.repository.ComplexShoppingmallRepository;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.ssg.starroadadmin.shop.enums.StoreSortType.NAME_ASC;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("매장 서비스 테스트")
class StoreServiceTest {

    @Autowired
    private StoreService storeService;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ComplexShoppingmallRepository complexShoppingmallRepository;

    static Manager mallManager;
    static Manager storeManager;
    static ComplexShoppingmall shoppingmall;

    @BeforeEach
    void init() {
        // 쇼핑몰 관리자 추가
        mallManager = managerRepository.save(Manager.builder().authority(Authority.MALL).build());
        // 쇼핑몰 추가
        shoppingmall = complexShoppingmallRepository.save(ComplexShoppingmall.builder().manager(mallManager).build());
        // 매장 관리자 추가
        storeManager = managerRepository.save(Manager.builder().authority(Authority.STORE).build());

        // 매장 추가
        storeService.createStore(mallManager.getId(), StoreRegisterRequest.builder()
                .storeName("테스트 매장1_1")
                .storeType("테스트 타입1")
                .storeFloor(1)
                .StoreManagerId(storeManager.getId())
                .build());
        storeService.createStore(mallManager.getId(), StoreRegisterRequest.builder()
                .storeName("테스트 매장1_2")
                .storeType("테스트 타입1")
                .storeFloor(1)
                .StoreManagerId(storeManager.getId())
                .build());
        storeService.createStore(mallManager.getId(), StoreRegisterRequest.builder()
                .storeName("테스트 매장2_1")
                .storeType("테스트 타입2")
                .storeFloor(2)
                .StoreManagerId(storeManager.getId())
                .build());
        storeService.createStore(mallManager.getId(), StoreRegisterRequest.builder()
                .storeName("테스트 매장2_2")
                .storeType("테스트 타입2")
                .storeFloor(2)
                .StoreManagerId(storeManager.getId())
                .build());
    }

    @Test
    @Transactional
    @DisplayName("새로운 매장 저장 성공 테스트")
    public void givenStoreRegisterRequest_whenCreateStore_thenStoreIsCreated() {
        // given

        // 매장 등록 요청
        StoreRegisterRequest request = StoreRegisterRequest.builder()
                .storeName("테스트 매장")
                .storeType("테스트 타입")
                .storeFloor(1)
                .StoreManagerId(storeManager.getId())
                .build();

        // when
        // 매장 추가
        Long storeId = storeService.createStore(mallManager.getId(), request);

        // then
        Store store = storeService.getStore(storeId);
        assertThat(store.getName()).isEqualTo(request.storeName());
        assertThat(store.getStoreType()).isEqualTo(request.storeType());
        assertThat(store.getFloor()).isEqualTo(request.storeFloor());
        assertThat(store.getManager().getId()).isEqualTo(request.StoreManagerId());
    }

    @Test
    @Transactional
    @DisplayName("매장 목록 조회 성공 테스트 - 모든 조건이 있는 경우")
    public void givenSearchStoreRequestAndNameIsNotNull_whenSearchStore_thenStoreListIsReturned() {
        // given

        // when
        // 매장 검색 요청 1
        SearchStoreRequest searchRequest = new SearchStoreRequest("테스트 매장1_1", 1, "테스트 타입1", NAME_ASC, 0, 10);
        Page<StoreListResponse> storeList = storeService.searchStoreList(mallManager.getId(), searchRequest);

        // 매장 검색 요청 2
        SearchStoreRequest searchRequest2 = new SearchStoreRequest("테스트 매장", 2, "테스트 타입2", NAME_ASC, 0, 10);
        Page<StoreListResponse> storeList2 = storeService.searchStoreList(mallManager.getId(), searchRequest2);

        // then
        // when 1
        assertThat(storeList.getTotalElements()).isEqualTo(1);

        // when 2
        assertThat(storeList2.getTotalElements()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("매장 목록 조회 성공 테스트 - 매장이름이 없는 경우")
    public void givenSearchStoreRequestAndNameIsNull_whenSearchStore_thenStoreListIsReturned() {
        // given


        // when
        // 매장 검색 요청
        SearchStoreRequest searchRequest = new SearchStoreRequest(null, 1, "테스트 타입1", NAME_ASC, 0, 10);
        Page<StoreListResponse> storeList = storeService.searchStoreList(mallManager.getId(), searchRequest);

        // then
        assertThat(storeList.getTotalElements()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("매장 목록 조회 성공 테스트 - 층수가 없는 경우")
    public void givenSearchStoreRequestAndFloorIsZero_whenSearchStore_thenStoreListIsReturned() {
        // given

        // when
        // 매장 검색 요청 1
        SearchStoreRequest searchRequest = new SearchStoreRequest("테스트 매장", 0, "테스트 타입1", NAME_ASC, 0, 10);
        Page<StoreListResponse> storeList = storeService.searchStoreList(mallManager.getId(), searchRequest);

        // 매장 검색 요청 2
        SearchStoreRequest searchRequest2 = new SearchStoreRequest("테스트 매장", 0, "테스트 타입2", NAME_ASC, 0, 10);
        Page<StoreListResponse> storeList2 = storeService.searchStoreList(mallManager.getId(), searchRequest2);

        // then
        assertThat(storeList.getTotalElements()).isEqualTo(2); // 업종이 1인 매장 2개
        assertThat(storeList2.getTotalElements()).isEqualTo(2); // 업종이 2인 매장 2개
    }
}