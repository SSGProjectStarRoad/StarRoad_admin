//package com.ssg.starroadadmin.shop.service;
//
//import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
//import com.ssg.starroadadmin.global.error.exception.ShopException;
//import com.ssg.starroadadmin.shop.dto.*;
//import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
//import com.ssg.starroadadmin.shop.entity.Store;
//import com.ssg.starroadadmin.shop.enums.Floor;
//import com.ssg.starroadadmin.shop.enums.StoreType;
//import com.ssg.starroadadmin.shop.repository.ComplexShoppingmallRepository;
//import com.ssg.starroadadmin.user.entity.Manager;
//import com.ssg.starroadadmin.user.enums.Authority;
//import com.ssg.starroadadmin.user.repository.ManagerRepository;
//import com.ssg.starroadadmin.user.repository.UserRepository;
//import com.ssg.starroadadmin.user.service.impl.ManagerServiceImpl;
//import com.ssg.starroadadmin.user.service.impl.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//
//import static com.ssg.starroadadmin.shop.enums.StoreSortType.NAME_ASC;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@SpringBootTest
//@DisplayName("매장 서비스 테스트")
//class StoreServiceTest {
//
//    @Autowired
//    private StoreService storeService;
//    @Autowired
//    private ManagerRepository managerRepository;
//    @Autowired
//    private ComplexShoppingmallRepository complexShoppingmallRepository;
//
//    static Manager mallManager;
//    static Manager storeManager, storeManager2;
//    static ComplexShoppingmall shoppingmall;
//    static Long store1Id;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserServiceImpl userServiceImpl;
//    @Autowired
//    private ManagerServiceImpl managerServiceImpl;
//
//    @Test
//    @DisplayName("매장 추가 ")
//    public void createStoreTest() {
//
//        StoreCreateRequest request = StoreCreateRequest.builder()
//                .createStoreFloor(Floor.BASEMENT_ONE)
//                .createStoreName("테스트2")
//                .createBusinessNumber("123-46-67450")
////                .createStoreGuideMap("WEFWEFE3WFWEF")
//                .createStoreManagerPassword("0000")
//                .createStoreType(StoreType.INTERIOR.name())
//                .createStoreManagerId("sungju2n@naver.com")
//                .createStoreManagerName("이한강")
//                .build();
//        Manager manager= managerServiceImpl.createManager(request.createStoreManagerId(), request.createStoreManagerPassword(), request.createBusinessNumber(), request.createStoreManagerName());
//        Long id = manager.getId();
//
//        storeService.createStore(5L,request,id);
//
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 목록 조회 성공 테스트 - 모든 조건이 있는 경우")
//    public void givenSearchStoreRequestAndNameIsNotNull_whenSearchStore_thenStoreListIsReturned() {
//        // given
//        Pageable pageable = PageRequest.of(0, 10);
//
//        // when
//        // 매장 검색 요청 1
//        SearchStoreRequest searchRequest = new SearchStoreRequest("테스트 매장1_1", Floor.FIRST, "테스트 타입1", NAME_ASC);
//        Page<StoreListResponse> storeList = storeService.searchStoreList(mallManager.getId(), searchRequest, pageable);
//
//        // 매장 검색 요청 2
//        SearchStoreRequest searchRequest2 = new SearchStoreRequest("테스트 매장", Floor.SECOND, "테스트 타입2", NAME_ASC);
//        Page<StoreListResponse> storeList2 = storeService.searchStoreList(mallManager.getId(), searchRequest2, pageable);
//
//        // then
//        // when 1
//        assertThat(storeList.getTotalElements()).isEqualTo(1);
//
//        // when 2
//        assertThat(storeList2.getTotalElements()).isEqualTo(2);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 목록 조회 성공 테스트 - 매장이름이 없는 경우")
//    public void givenSearchStoreRequestAndNameIsNull_whenSearchStore_thenStoreListIsReturned() {
//        // given
//        Pageable pageable = PageRequest.of(0, 10);
//
//        // when
//        // 매장 검색 요청
//        SearchStoreRequest searchRequest = new SearchStoreRequest(null, Floor.FIRST, "테스트 타입1", NAME_ASC);
//        Page<StoreListResponse> storeList = storeService.searchStoreList(mallManager.getId(), searchRequest, pageable);
//
//        // then
//        assertThat(storeList.getTotalElements()).isEqualTo(2);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 목록 조회 성공 테스트 - 층수가 없는 경우")
//    public void givenSearchStoreRequestAndFloorIsZero_whenSearchStore_thenStoreListIsReturned() {
//        // given
//        Pageable pageable = PageRequest.of(0, 10);
//
//        // when
//        // 매장 검색 요청 1
//        SearchStoreRequest searchRequest = new SearchStoreRequest("테스트 매장", Floor.FIRST, "테스트 타입1", NAME_ASC);
//        Page<StoreListResponse> storeList = storeService.searchStoreList(mallManager.getId(), searchRequest, pageable);
//
//        // 매장 검색 요청 2
//        SearchStoreRequest searchRequest2 = new SearchStoreRequest("테스트 매장", Floor.SECOND, "테스트 타입2", NAME_ASC);
//        Page<StoreListResponse> storeList2 = storeService.searchStoreList(mallManager.getId(), searchRequest2, pageable);
//
//        // then
//        assertThat(storeList.getTotalElements()).isEqualTo(2); // 업종이 1인 매장 2개
//        assertThat(storeList2.getTotalElements()).isEqualTo(2); // 업종이 2인 매장 2개
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 정보 수정 성공 테스트")
//    public void givenStoreModifyRequest_whenUpdateStore_thenStoreIsUpdated() {
//        // given
//        Long managerid = storeManager.getId();
//        Long storeId = store1Id;
//        StoreModifyRequest request = StoreModifyRequest.builder()
//                .contents("수정된 매장 설명")
//                .operatingTime("09:00 ~ 21:00")
//                .contactNumber("010-1234-5678")
//                .build();
//        StoreResponse store = storeService.getStore(storeManager.getId(), storeId);
//
//        // when
//        // 매장 정보 수정 요청
//        storeService.updateStore(managerid, store.id(), request);
//
//        // then
//        StoreResponse updatedStore = storeService.getStore(storeManager.getId(), store.id());
//        assertThat(updatedStore.id()).isEqualTo(store1Id);
//        assertThat(updatedStore.managerId()).isEqualTo(managerid);
//        assertThat(updatedStore.name()).isEqualTo(store.name());
//        assertThat(updatedStore.storeType()).isEqualTo(store.storeType());
//        assertThat(updatedStore.imagePath()).isEqualTo(store.imagePath());
//        assertThat(updatedStore.contents()).isEqualTo(request.contents());
//        assertThat(updatedStore.floor()).isEqualTo(store.floor());
//        assertThat(updatedStore.operatingTime()).isEqualTo(request.operatingTime());
//        assertThat(updatedStore.contactNumber()).isEqualTo(request.contactNumber());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 정보 수정 실패 테스트 - 매장이 없는 경우")
//    public void givenStoreModifyRequestAndStoreIdIsNotValid_whenUpdateStore_thenStoreIsNotFound() {
//        // given
//        Long managerid = storeManager.getId();
//        Long storeId = 0L;
//        StoreModifyRequest request = StoreModifyRequest.builder()
//                .contents("수정된 매장 설명")
//                .operatingTime("09:00 ~ 21:00")
//                .contactNumber("010-1234-5678")
//                .build();
//
//        // when & then
//        // 매장 정보 수정 요청
//        assertThatThrownBy(() -> storeService.updateStore(managerid, storeId, request))
//                .isInstanceOf(ShopException.class)
//                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 정보 수정 실패 테스트 - 매장 관리자가 아닌 경우")
//    public void givenStoreModifyRequestAndManagerIsNotStoreManager_whenUpdateStore_thenAccessDenied() {
//        // given
//        Long managerid = storeManager2.getId();
//        Long storeId = store1Id;
//        StoreModifyRequest request = StoreModifyRequest.builder()
//                .contents("수정된 매장 설명")
//                .operatingTime("09:00 ~ 21:00")
//                .contactNumber("010-1234-5678")
//                .build();
//
//        // when & then
//        // 매장 정보 수정 요청
//        assertThatThrownBy(() -> storeService.updateStore(managerid, storeId, request))
//                .isInstanceOf(ShopException.class)
//                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 이미지 수정 성공 테스트")
//    public void givenImagePath_whenUpdateStoreImage_thenStoreImageIsUpdated() {
//        // given
//        Long managerid = storeManager.getId();
//        Long storeId = store1Id;
//        MultipartFile multipartFile = (MultipartFile) new File("/test");
//
//        // when
//        // 매장 이미지 수정 요청
//        storeService.updateStoreImage(managerid, storeId, multipartFile);
//
//        // then
//        StoreResponse updatedStore = storeService.getStore(storeManager.getId(), storeId);
//        assertThat(updatedStore.id()).isEqualTo(store1Id);
//        assertThat(updatedStore.managerId()).isEqualTo(managerid);
//        assertThat(updatedStore.name()).isEqualTo(updatedStore.name());
//        assertThat(updatedStore.storeType()).isEqualTo(updatedStore.storeType());
////        assertThat(updatedStore.imagePath()).isEqualTo(imagePath);
//        assertThat(updatedStore.contents()).isEqualTo(updatedStore.contents());
//        assertThat(updatedStore.floor()).isEqualTo(updatedStore.floor());
//        assertThat(updatedStore.operatingTime()).isEqualTo(updatedStore.operatingTime());
//        assertThat(updatedStore.contactNumber()).isEqualTo(updatedStore.contactNumber());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 이미지 수정 실패 테스트 - 매장이 없는 경우")
//    public void givenImagePathAndStoreIdIsNotValid_whenUpdateStoreImage_thenStoreIsNotFound() {
//        // given
//        Long managerid = storeManager.getId();
//        Long storeId = 0L;
//        MultipartFile multipartFile = (MultipartFile) new File("/test");
//
//        // when & then
//        // 매장 이미지 수정 요청
//        assertThatThrownBy(() -> storeService.updateStoreImage(managerid, storeId, multipartFile))
//                .isInstanceOf(ShopException.class)
//                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 이미지 수정 실패 테스트 - 매장 관리자가 아닌 경우")
//    public void givenImagePathAndManagerIsNotStoreManager_whenUpdateStoreImage_thenAccessDenied() {
//        // given
//        Long managerid = storeManager2.getId();
//        Long storeId = store1Id;
//        MultipartFile multipartFile = (MultipartFile) new File("/test");
//
//        // when & then
//        // 매장 이미지 수정 요청
//        assertThatThrownBy(() -> storeService.updateStoreImage(managerid, storeId, multipartFile))
//                .isInstanceOf(ShopException.class)
//                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 삭제 성공 테스트")
//    public void givenStoreId_whenDeleteStore_thenStoreIsDeleted() {
//        // given
//        Long managerid = storeManager.getId();
//        Long storeId = store1Id;
//
//        // when
//        // 매장 삭제 요청
//        storeService.deleteStore(managerid, storeId);
//
//        // then
//        assertThatThrownBy(() -> storeService.getStore(managerid, storeId))
//                .isInstanceOf(ShopException.class)
//                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 삭제 실패 테스트 - 매장이 없는 경우")
//    public void givenStoreIdIsNotValid_whenDeleteStore_thenStoreIsNotFound() {
//        // given
//        Long managerid = storeManager.getId();
//        Long storeId = 0L;
//
//        // when & then
//        // 매장 삭제 요청
//        assertThatThrownBy(() -> storeService.deleteStore(managerid, storeId))
//                .isInstanceOf(ShopException.class)
//                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("매장 삭제 실패 테스트 - 매장 관리자가 아닌 경우")
//    public void givenManagerIsNotStoreManager_whenDeleteStore_thenAccessDenied() {
//        // given
//        Long managerid = storeManager2.getId();
//        Long storeId = store1Id;
//
//        // when & then
//        // 매장 삭제 요청
//        assertThatThrownBy(() -> storeService.deleteStore(managerid, storeId))
//                .isInstanceOf(ShopException.class)
//                .hasMessage(ShopErrorCode.STORE_NOT_FOUND.getDescription());
//    }
//}