package com.ssg.starroadadmin.shop.service;

import com.ssg.starroadadmin.shop.dto.StoreRegisterRequest;
import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.repository.ComplexShoppingmallRepository;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("매장 서비스 테스트")
class StoreServiceTest {

    @Autowired private StoreService storeService;
    @Autowired private ManagerRepository managerRepository;
    @Autowired private ComplexShoppingmallRepository complexShoppingmallRepository;


    @Test
    @DisplayName("새로운 매장 저장 성공 테스트")
    public void givenStoreRegisterRequest_whenCreateStore_thenStoreIsCreated() {
        // given
        // 쇼핑몰 관리자 추가
        Manager mallManager = managerRepository.save(Manager.builder().authority(Authority.MALL).build());
        // 쇼핑몰 추가
        complexShoppingmallRepository.save(ComplexShoppingmall.builder().manager(mallManager).build());
        // 매장 관리자 추가
        Manager storeManager = managerRepository.save(Manager.builder().authority(Authority.STORE).build());

        // 매장 등록 요청
        StoreRegisterRequest request = StoreRegisterRequest.builder()
                .storeName("테스트 매장")
                .storeType("테스트 타입")
                .storeFloor(1)
                .StoreManagerId(storeManager.getId())
                .build();

        // when
        // 매장 추가
        storeService.createStore(mallManager.getId(), request);

        // then
        Store store = storeService.getStore(1L);
        assertThat(store.getName()).isEqualTo(request.storeName());
        assertThat(store.getStoreType()).isEqualTo(request.storeType());
        assertThat(store.getFloor()).isEqualTo(request.storeFloor());
        assertThat(store.getManager().getId()).isEqualTo(request.StoreManagerId());
    }
}