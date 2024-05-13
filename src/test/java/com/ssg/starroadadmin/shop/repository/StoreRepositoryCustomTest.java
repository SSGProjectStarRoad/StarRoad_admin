package com.ssg.starroadadmin.shop.repository;

import com.jayway.jsonpath.internal.function.sequence.First;
import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.enums.Floor;
import com.ssg.starroadadmin.shop.enums.StoreSortType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ssg.starroadadmin.shop.enums.Floor.FIRST;
import static com.ssg.starroadadmin.shop.enums.Floor.SECOND;
import static com.ssg.starroadadmin.shop.enums.StoreSortType.NAME_ASC;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("StoreRepositoryCustom 테스트 - QUERYDSL")
class StoreRepositoryCustomTest {

    @Autowired
    private StoreRepositoryCustom storeRepositoryCustom;

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    ComplexShoppingmallRepository complexShoppingmallRepository;

    static ComplexShoppingmall shoppingmall;

    @BeforeEach
    void init() {
        shoppingmall = complexShoppingmallRepository.save(ComplexShoppingmall.builder().build());

        for (int i = 0; i < 20; i++) {
            storeRepository.save(Store.builder()
                    .name(String.format("테스트 상점1_%02d", i))
                    .storeType("테스트 타입" + (i % 2))
                    .floor(FIRST)
                    .complexShoppingmall(shoppingmall)
                    .build());

            storeRepository.save(Store.builder()
                    .name(String.format("테스트 상점2_%02d", i))
                    .storeType("테스트 타입" + (i % 2))
                    .floor(Floor.SECOND)
                    .complexShoppingmall(shoppingmall)
                    .build());
        }
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
                shoppingmall.getId(), null, FIRST, "테스트 타입1", sortType, pageable);

        // 2. 2층 상점 목록 조회
        Page<StoreListResponse> storeList2 = storeRepositoryCustom.findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
                shoppingmall.getId(), null, Floor.SECOND, "테스트 타입0", sortType, pageable);

        // then
        // when 1 검증
        assertThat(storeList1).isNotNull();
        assertThat(storeList1.getTotalElements()).isEqualTo(10);
        List<StoreListResponse> content1 = storeList1.getContent();
        assertThat(content1).extracting("name").containsExactly("테스트 상점1_01", "테스트 상점1_03", "테스트 상점1_05", "테스트 상점1_07", "테스트 상점1_09", "테스트 상점1_11", "테스트 상점1_13", "테스트 상점1_15", "테스트 상점1_17", "테스트 상점1_19");
        assertThat(content1).extracting("storeType").containsExactly("테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1");
        assertThat(content1).extracting("floor").containsExactly(FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST);

        // when 2 검증
        assertThat(storeList2).isNotNull();
        assertThat(storeList2.getTotalElements()).isEqualTo(10);
        List<StoreListResponse> content2 = storeList2.getContent();
        assertThat(content2).extracting("name").containsExactly("테스트 상점2_00", "테스트 상점2_02", "테스트 상점2_04", "테스트 상점2_06", "테스트 상점2_08", "테스트 상점2_10", "테스트 상점2_12", "테스트 상점2_14", "테스트 상점2_16", "테스트 상점2_18");
        assertThat(content2).extracting("storeType").containsExactly("테스트 타입0", "테스트 타입0", "테스트 타입0", "테스트 타입0", "테스트 타입0", "테스트 타입0", "테스트 타입0", "테스트 타입0", "테스트 타입0", "테스트 타입0");
        assertThat(content2).extracting("floor").containsExactly(SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND, SECOND);
    }

    @Test
    @Transactional
    @DisplayName("상점 목록 조회 성공 테스트 - (name : not null)")
    public void givenNameAndFloorAndStoreTypeAndPageable_whenFindByNameContainingOrFloorOrStoreType_thenStoreListIsFound() {
        // given
        StoreSortType sortType = NAME_ASC;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        // 1. name이 "테스트 상점"이고 1층에 있는 상점 목록 조회
        Page<StoreListResponse> storeList1 = storeRepositoryCustom.findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
                shoppingmall.getId(), "테스트 상점", FIRST, "테스트 타입1", sortType, pageable);

        // 2. name이 "상점2"이고 2층에 있는 상점 목록 조회(검색 결과 없음)
        Page<StoreListResponse> storeList2 = storeRepositoryCustom.findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
                shoppingmall.getId(), "상점2", Floor.SECOND, "테스트 타입0", sortType, pageable);

        // then
        // when 1 검증
        assertThat(storeList1).isNotNull();
        assertThat(storeList1.getTotalElements()).isEqualTo(10);
        List<StoreListResponse> content1 = storeList1.getContent();
        assertThat(content1).extracting("name").containsExactly("테스트 상점1_01", "테스트 상점1_03", "테스트 상점1_05", "테스트 상점1_07", "테스트 상점1_09", "테스트 상점1_11", "테스트 상점1_13", "테스트 상점1_15", "테스트 상점1_17", "테스트 상점1_19");
        assertThat(content1).extracting("storeType").containsExactly("테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1");
        assertThat(content1).extracting("floor").containsExactly(FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST, FIRST);

        // when 2 검증
        assertThat(storeList2).isNotNull();
        assertThat(storeList2.getTotalElements()).isEqualTo(10);
    }

    @Test
    @Transactional
    @DisplayName("상점 목록 조회 성공 테스트 - (name : not null, floor : 0)")
    public void givenNameIsNotNullAndFloorIsZeroAndStoreTypeAndPageable_whenFindByNameContainingOrFloorOrStoreType_thenStoreListIsFound() {
        // given
        StoreSortType sortType = NAME_ASC;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        // 1. name이 "테스트 상점"이고 1층에 있는 상점 목록 조회
        Page<StoreListResponse> storeList1 = storeRepositoryCustom.findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
                shoppingmall.getId(), "테스트 상점", FIRST, "테스트 타입1", sortType, pageable);

        // 2. name이 "상점2"이고 2층에 있는 상점 목록 조회(검색 결과 없음)
        Page<StoreListResponse> storeList2 = storeRepositoryCustom.findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
                shoppingmall.getId(), "상점2", FIRST, "테스트 타입0", sortType, pageable);

        // then
        // when 1 검증
        assertThat(storeList1).isNotNull();
        assertThat(storeList1.getTotalElements()).isEqualTo(10);
        List<StoreListResponse> content1 = storeList1.getContent();
        assertThat(content1).extracting("name").containsExactly("테스트 상점1_01", "테스트 상점1_03", "테스트 상점1_05", "테스트 상점1_07", "테스트 상점1_09", "테스트 상점1_11", "테스트 상점1_13", "테스트 상점1_15", "테스트 상점1_17", "테스트 상점1_19");
        assertThat(content1).extracting("storeType").containsExactly("테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1", "테스트 타입1");
    }

    @Test
    @Transactional
    @DisplayName("상점 목록 조회 성공 테스트 - (name : null, floor : 0, storeType : null)")
    public void givenNameIsNullAndFloorIsZeroAndStoreTypeIsNullAndPageable_whenFindByNameContainingOrFloorOrStoreType_thenStoreListIsFound() {
        // given
        StoreSortType sortType = NAME_ASC;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        // 1. name이 "테스트 상점"이고 1층에 있는 상점 목록 조회
        Page<StoreListResponse> storeList = storeRepositoryCustom.findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
                shoppingmall.getId(), null, FIRST, null, sortType, pageable);

        // then
        // when 1 검증
        assertThat(storeList).isNotNull();
        assertThat(storeList.getTotalElements()).isEqualTo(20);
        List<StoreListResponse> content = storeList.getContent();
        assertThat(content).extracting("name").containsExactly("테스트 상점1_00", "테스트 상점1_01", "테스트 상점1_02", "테스트 상점1_03", "테스트 상점1_04", "테스트 상점1_05", "테스트 상점1_06", "테스트 상점1_07", "테스트 상점1_08", "테스트 상점1_09");
    }
}