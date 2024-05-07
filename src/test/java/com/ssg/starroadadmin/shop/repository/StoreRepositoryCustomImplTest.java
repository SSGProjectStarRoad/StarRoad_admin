package com.ssg.starroadadmin.shop.repository;

import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.enums.StoreSortType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ssg.starroadadmin.shop.enums.StoreSortType.NAME_ASC;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("StoreRepositoryCustomImpl 테스트 - QUERYDSL")
class StoreRepositoryCustomImplTest {

    @Autowired
    private StoreRepositoryCustom storeRepositoryCustom;

    @Autowired
    private StoreRepository storeRepository;
    @Autowired ComplexShoppingmallRepository complexShoppingmallRepository;

    static ComplexShoppingmall shoppingmall;

    @BeforeEach
    void init() {
        shoppingmall = complexShoppingmallRepository.save(ComplexShoppingmall.builder().build());
        Store store1_1 = Store.builder()
                .complexShoppingmall(shoppingmall)
                .name("테스트 상점1_1")
                .storeType("테스트 타입1")
                .floor(1)
                .build();
        Store store1_2 = Store.builder()
                .complexShoppingmall(shoppingmall)
                .name("테스트 상점1_2")
                .storeType("테스트 타입2")
                .floor(1)
                .build();
        Store store2_1 = Store.builder()
                .complexShoppingmall(shoppingmall)
                .name("테스트 상점2_1")
                .storeType("테스트 타입1")
                .floor(2)
                .build();
        Store store2_2 = Store.builder()
                .complexShoppingmall(shoppingmall)
                .name("테스트 상점2_2")
                .storeType("테스트 타입2")
                .floor(2)
                .build();

        // 상점 데이터를 저장
        storeRepository.save(store1_1);
        storeRepository.save(store1_2);
        storeRepository.save(store2_1);
        storeRepository.save(store2_2);
    }

    @AfterEach
    void clean() {
        storeRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("상점 목록 조회 성공 테스트 - (name : null)")
    public void givenFloorAndStoreTypeAndPageable_whenFindByNameContainingOrFloorOrStoreType_thenStoreListIsFound() {
        // given
        StoreSortType sortType = NAME_ASC;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        // 1. 1층 상점 목록 조회
        Page<StoreListResponse> storeList1 = storeRepositoryCustom.findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
                shoppingmall.getId(),null, 1, "테스트 타입1", sortType, pageable);

        // 2. 2층 상점 목록 조회
        Page<StoreListResponse> storeList2 = storeRepositoryCustom.findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
                shoppingmall.getId(),null, 2, "테스트 타입2", sortType, pageable);

        // then
        // when 1 검증
        assertThat(storeList1).isNotNull();
        assertThat(storeList1.getTotalElements()).isEqualTo(1);
        List<StoreListResponse> content1 = storeList1.getContent();
        assertThat(content1).extracting("name").containsExactly("테스트 상점1_1");
        assertThat(content1).extracting("storeType").containsExactly("테스트 타입1");
        assertThat(content1).extracting("floor").containsExactly( 1);

        // when 2 검증
        assertThat(storeList2).isNotNull();
        assertThat(storeList2.getTotalElements()).isEqualTo(1);
        List<StoreListResponse> content2 = storeList2.getContent();
        assertThat(content2).extracting("name").containsExactly("테스트 상점2_2");
        assertThat(content2).extracting("storeType").containsExactly("테스트 타입2");
        assertThat(content2).extracting("floor").containsExactly(2);
    }

    @Test
    @Transactional
    @DisplayName("상점 목록 조회 성공 테스트 - (name : not null)")
    public void givenNameAndFloorAndStoreTypeAndPageable_whenFindByNameContainingOrFloorOrStoreType_thenStoreListIsFound() {
        // given
        StoreSortType sortType = NAME_ASC;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        // 1. name이 "1_1"이고 1층에 있는 상점 목록 조회
        Page<StoreListResponse> storeList1 = storeRepositoryCustom.findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
                shoppingmall.getId(),"1_1", 1, "테스트 타입1", sortType, pageable);

        // 2. name이 "1_2"이고 2층에 있는 상점 목록 조회(검색 결과 없음)
        Page<StoreListResponse> storeList2 = storeRepositoryCustom.findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
                shoppingmall.getId(),"1_2", 2, "테스트 타입2", sortType, pageable);

        // then
        // when 1 검증
        assertThat(storeList1).isNotNull();
        assertThat(storeList1.getTotalElements()).isEqualTo(1);
        List<StoreListResponse> content1 = storeList1.getContent();
        assertThat(content1).extracting("name").containsExactly("테스트 상점1_1");
        assertThat(content1).extracting("storeType").containsExactly("테스트 타입1");
        assertThat(content1).extracting("floor").containsExactly(1);

        // when 2 검증
        assertThat(storeList2).isNotNull();
        assertThat(storeList2.getTotalElements()).isEqualTo(0);
    }
}