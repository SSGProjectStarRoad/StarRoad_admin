package com.ssg.starroadadmin.shop.service;

import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import com.ssg.starroadadmin.global.error.exception.ShopException;
import com.ssg.starroadadmin.shop.dto.SearchStoreRequest;
import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.dto.StoreModifyRequest;
import com.ssg.starroadadmin.shop.dto.StoreRegisterRequest;
import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.enums.Floor;
import com.ssg.starroadadmin.shop.repository.ComplexShoppingmallRepository;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static com.ssg.starroadadmin.shop.enums.StoreSortType.NAME_ASC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    static Manager storeManager, storeManager2;
    static ComplexShoppingmall shoppingmall;
    static Long store1Id;

    @BeforeEach
    void init() {
        // 쇼핑몰 관리자 추가
        mallManager = managerRepository.save(Manager.builder().authority(Authority.MALL).build());
        // 쇼핑몰 추가
        shoppingmall = complexShoppingmallRepository.save(ComplexShoppingmall.builder().manager(mallManager).build());
        // 매장 관리자 추가
        storeManager = managerRepository.save(Manager.builder().authority(Authority.STORE).build());
        storeManager2 = managerRepository.save(Manager.builder().authority(Authority.STORE).build());

        // 매장 추가
        store1Id = storeService.createStore(mallManager.getId(), StoreRegisterRequest.builder()
                .storeName("테스트 매장1_1")
                .storeType("테스트 타입1")
                .storeFloor(Floor.FIRST)
                .StoreManagerId(storeManager.getId())
                .build());
        storeService.createStore(mallManager.getId(), StoreRegisterRequest.builder()
                .storeName("테스트 매장1_2")
                .storeType("테스트 타입1")
                .storeFloor(Floor.FIRST)
                .StoreManagerId(storeManager.getId())
                .build());
        storeService.createStore(mallManager.getId(), StoreRegisterRequest.builder()
                .storeName("테스트 매장2_1")
                .storeType("테스트 타입2")
                .storeFloor(Floor.SECOND)
                .StoreManagerId(storeManager.getId())
                .build());
        storeService.createStore(mallManager.getId(), StoreRegisterRequest.builder()
                .storeName("테스트 매장2_2")
                .storeType("테스트 타입2")
                .storeFloor(Floor.SECOND)
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
                .storeFloor(Floor.FIRST)
                .StoreManagerId(storeManager.getId())
                .build();

        // when
        // 매장 추가
        Long storeId = storeService.createStore(mallManager.getId(), request);

        // then
        Store store = storeService.getStore(storeManager.getId(), storeId);
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
        SearchStoreRequest searchRequest = new SearchStoreRequest("테스트 매장1_1", Floor.FIRST, "테스트 타입1", NAME_ASC, 0, 10);
        Page<StoreListResponse> storeList = storeService.searchStoreList(mallManager.getId(), searchRequest);

        // 매장 검색 요청 2
        SearchStoreRequest searchRequest2 = new SearchStoreRequest("테스트 매장", Floor.SECOND, "테스트 타입2", NAME_ASC, 0, 10);
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
        SearchStoreRequest searchRequest = new SearchStoreRequest(null, Floor.FIRST, "테스트 타입1", NAME_ASC, 0, 10);
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
        SearchStoreRequest searchRequest = new SearchStoreRequest("테스트 매장", Floor.FIRST, "테스트 타입1", NAME_ASC, 0, 10);
        Page<StoreListResponse> storeList = storeService.searchStoreList(mallManager.getId(), searchRequest);

        // 매장 검색 요청 2
        SearchStoreRequest searchRequest2 = new SearchStoreRequest("테스트 매장", Floor.FIRST, "테스트 타입2", NAME_ASC, 0, 10);
        Page<StoreListResponse> storeList2 = storeService.searchStoreList(mallManager.getId(), searchRequest2);

        // then
        assertThat(storeList.getTotalElements()).isEqualTo(2); // 업종이 1인 매장 2개
        assertThat(storeList2.getTotalElements()).isEqualTo(2); // 업종이 2인 매장 2개
    }

    @Test
    @Transactional
    @DisplayName("매장 정보 수정 성공 테스트")
    public void givenStoreModifyRequest_whenUpdateStore_thenStoreIsUpdated() {
        // given
        Long managerid = storeManager.getId();
        Long storeId = store1Id;
        StoreModifyRequest request = StoreModifyRequest.builder()
                .contents("수정된 매장 설명")
                .operatingTime("09:00 ~ 21:00")
                .contactNumber("010-1234-5678")
                .build();
        Store store = storeService.getStore(storeManager.getId(), storeId);

        // when
        // 매장 정보 수정 요청
        storeService.updateStore(managerid, store.getId(), request);

        // then
        Store updatedStore = storeService.getStore(storeManager.getId(), store.getId());
        assertThat(updatedStore.getId()).isEqualTo(store1Id);
        assertThat(updatedStore.getManager().getId()).isEqualTo(managerid);
        assertThat(updatedStore.getName()).isEqualTo(store.getName());
        assertThat(updatedStore.getStoreType()).isEqualTo(store.getStoreType());
        assertThat(updatedStore.getImagePath()).isEqualTo(store.getImagePath());
        assertThat(updatedStore.getContents()).isEqualTo(request.contents());
        assertThat(updatedStore.getFloor()).isEqualTo(store.getFloor());
        assertThat(updatedStore.getOperatingTime()).isEqualTo(request.operatingTime());
        assertThat(updatedStore.getContactNumber()).isEqualTo(request.contactNumber());
    }

    @Test
    @Transactional
    @DisplayName("매장 정보 수정 실패 테스트 - 매장이 없는 경우")
    public void givenStoreModifyRequestAndStoreIdIsNotValid_whenUpdateStore_thenStoreIsNotFound() {
        // given
        Long managerid = storeManager.getId();
        Long storeId = 0L;
        StoreModifyRequest request = StoreModifyRequest.builder()
                .contents("수정된 매장 설명")
                .operatingTime("09:00 ~ 21:00")
                .contactNumber("010-1234-5678")
                .build();

        // when & then
        // 매장 정보 수정 요청
        assertThatThrownBy(() -> storeService.updateStore(managerid, storeId, request))
                .isInstanceOf(ShopException.class)
                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
    }

    @Test
    @Transactional
    @DisplayName("매장 정보 수정 실패 테스트 - 매장 관리자가 아닌 경우")
    public void givenStoreModifyRequestAndManagerIsNotStoreManager_whenUpdateStore_thenAccessDenied() {
        // given
        Long managerid = storeManager2.getId();
        Long storeId = store1Id;
        StoreModifyRequest request = StoreModifyRequest.builder()
                .contents("수정된 매장 설명")
                .operatingTime("09:00 ~ 21:00")
                .contactNumber("010-1234-5678")
                .build();

        // when & then
        // 매장 정보 수정 요청
        assertThatThrownBy(() -> storeService.updateStore(managerid, storeId, request))
                .isInstanceOf(ShopException.class)
                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
    }

    @Test
    @Transactional
    @DisplayName("매장 이미지 수정 성공 테스트")
    public void givenImagePath_whenUpdateStoreImage_thenStoreImageIsUpdated() {
        // given
        Long managerid = storeManager.getId();
        Long storeId = store1Id;
        String imagePath = "testImagePath";

        // when
        // 매장 이미지 수정 요청
        storeService.updateStoreImage(managerid, storeId, imagePath);

        // then
        Store updatedStore = storeService.getStore(storeManager.getId(), storeId);
        assertThat(updatedStore.getId()).isEqualTo(store1Id);
        assertThat(updatedStore.getManager().getId()).isEqualTo(managerid);
        assertThat(updatedStore.getName()).isEqualTo(updatedStore.getName());
        assertThat(updatedStore.getStoreType()).isEqualTo(updatedStore.getStoreType());
        assertThat(updatedStore.getImagePath()).isEqualTo(imagePath);
        assertThat(updatedStore.getContents()).isEqualTo(updatedStore.getContents());
        assertThat(updatedStore.getFloor()).isEqualTo(updatedStore.getFloor());
        assertThat(updatedStore.getOperatingTime()).isEqualTo(updatedStore.getOperatingTime());
        assertThat(updatedStore.getContactNumber()).isEqualTo(updatedStore.getContactNumber());
    }

    @Test
    @Transactional
    @DisplayName("매장 이미지 수정 실패 테스트 - 매장이 없는 경우")
    public void givenImagePathAndStoreIdIsNotValid_whenUpdateStoreImage_thenStoreIsNotFound() {
        // given
        Long managerid = storeManager.getId();
        Long storeId = 0L;
        String imagePath = "testImagePath";

        // when & then
        // 매장 이미지 수정 요청
        assertThatThrownBy(() -> storeService.updateStoreImage(managerid, storeId, imagePath))
                .isInstanceOf(ShopException.class)
                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
    }

    @Test
    @Transactional
    @DisplayName("매장 이미지 수정 실패 테스트 - 매장 관리자가 아닌 경우")
    public void givenImagePathAndManagerIsNotStoreManager_whenUpdateStoreImage_thenAccessDenied() {
        // given
        Long managerid = storeManager2.getId();
        Long storeId = store1Id;
        String imagePath = "testImagePath";

        // when & then
        // 매장 이미지 수정 요청
        assertThatThrownBy(() -> storeService.updateStoreImage(managerid, storeId, imagePath))
                .isInstanceOf(ShopException.class)
                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
    }

    @Test
    @Transactional
    @DisplayName("매장 삭제 성공 테스트")
    public void givenStoreId_whenDeleteStore_thenStoreIsDeleted() {
        // given
        Long managerid = storeManager.getId();
        Long storeId = store1Id;

        // when
        // 매장 삭제 요청
        storeService.deleteStore(managerid, storeId);

        // then
        assertThatThrownBy(() -> storeService.getStore(managerid, storeId))
                .isInstanceOf(ShopException.class)
                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
    }

    @Test
    @Transactional
    @DisplayName("매장 삭제 실패 테스트 - 매장이 없는 경우")
    public void givenStoreIdIsNotValid_whenDeleteStore_thenStoreIsNotFound() {
        // given
        Long managerid = storeManager.getId();
        Long storeId = 0L;

        // when & then
        // 매장 삭제 요청
        assertThatThrownBy(() -> storeService.deleteStore(managerid, storeId))
                .isInstanceOf(ShopException.class)
                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
    }

    @Test
    @Transactional
    @DisplayName("매장 삭제 실패 테스트 - 매장 관리자가 아닌 경우")
    public void givenManagerIsNotStoreManager_whenDeleteStore_thenAccessDenied() {
        // given
        Long managerid = storeManager2.getId();
        Long storeId = store1Id;

        // when & then
        // 매장 삭제 요청
        assertThatThrownBy(() -> storeService.deleteStore(managerid, storeId))
                .isInstanceOf(ShopException.class)
                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
    }
}