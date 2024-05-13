package com.ssg.starroadadmin.shop.repository;
import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.enums.Floor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("상점 레파지토리 테스트")
public class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("새로운 상점 저장 성공 테스트")
    public void givenNewStore_whenSave_thenStoreIsSaved() {
        // given
        Store store = Store.builder()
                .name("테스트 상점")
                .storeType("테스트 타입")
                .imagePath("테스트/이미지/경로")
                .contents("테스트 내용")
                .floor(Floor.FIRST)
                .operatingTime("09:00 ~ 18:00")
                .contactNumber("123-456-7890")
                .build();

        // when
        Store savedStore = storeRepository.save(store);

        // then
        assertThat(savedStore.getId()).isNotNull();
        assertThat(savedStore.getName()).isEqualTo(store.getName());
        assertThat(savedStore.getStoreType()).isEqualTo(store.getStoreType());
        assertThat(savedStore.getImagePath()).isEqualTo(store.getImagePath());
        assertThat(savedStore.getContents()).isEqualTo(store.getContents());
        assertThat(savedStore.getFloor()).isEqualTo(store.getFloor());
        assertThat(savedStore.getOperatingTime()).isEqualTo(store.getOperatingTime());
        assertThat(savedStore.getContactNumber()).isEqualTo(store.getContactNumber());
    }

    @Test
    @DisplayName("저장된 상점 조회 성공 테스트")
    public void givenSavedStore_whenFindById_thenStoreIsFound() {
        // given
        Store store = Store.builder()
                .name("테스트 상점")
                .storeType("테스트 타입")
                .imagePath("테스트/이미지/경로")
                .contents("테스트 내용")
                .floor(Floor.FIRST)
                .operatingTime("09:00 ~ 18:00")
                .contactNumber("123-456-7890")
                .build();
        Store savedStore = storeRepository.save(store);

        // when
        Optional<Store> optionalStore = storeRepository.findById(savedStore.getId());

        // then
        assertThat(optionalStore).isPresent();
        Store foundStore = optionalStore.get();
        assertThat(foundStore.getName()).isEqualTo(store.getName());
        assertThat(foundStore.getStoreType()).isEqualTo(store.getStoreType());
        assertThat(foundStore.getImagePath()).isEqualTo(store.getImagePath());
        assertThat(foundStore.getContents()).isEqualTo(store.getContents());
        assertThat(foundStore.getFloor()).isEqualTo(store.getFloor());
        assertThat(foundStore.getOperatingTime()).isEqualTo(store.getOperatingTime());
        assertThat(foundStore.getContactNumber()).isEqualTo(store.getContactNumber());
    }

    @Test
    @DisplayName("상점 정보 수정 성공 테스트 1")
    public void givenExistingStore_whenUpdate_thenStoreIsUpdated() {
        // given
        Store store = Store.builder()
                .name("테스트 상점")
                .storeType("테스트 타입")
                .imagePath("테스트/이미지/경로")
                .contents("테스트 내용")
                .floor(Floor.FIRST)
                .operatingTime("09:00 ~ 18:00")
                .contactNumber("123-456-7890")
                .build();
        Store savedStore = storeRepository.save(store);

        // when
        Store updateStore = Store.builder()
                .id(savedStore.getId())
                .name(savedStore.getName())
                .storeType(savedStore.getStoreType())
                .imagePath("테스트/수정된/이미지/경로")
                .contents("수정된 테스트 내용")
                .floor(Floor.SECOND)
                .operatingTime("10:00 ~ 19:00")
                .contactNumber("098-765-4321")
                .build();

        Store updatedStore = storeRepository.save(updateStore);

        // then
        assertThat(updatedStore.getId()).isEqualTo(savedStore.getId());
        assertThat(updatedStore.getName()).isEqualTo(savedStore.getName());
    }

    @Test
    @DisplayName("상점 정보 수정 성공 테스트 2 - updateInfo 메소드 사용")
    public void givenExistingStore_whenUpdateInfo_thenStoreIsUpdated() {
        // given
        Store store = Store.builder()
                .name("테스트 상점")
                .storeType("테스트 타입")
                .imagePath("테스트/이미지/경로")
                .contents("테스트 내용")
                .floor(Floor.FIRST)
                .operatingTime("09:00 ~ 18:00")
                .contactNumber("123-456-7890")
                .build();
        Store savedStore = storeRepository.save(store);

        // when
        String newContents = "새로운 내용";
        String newOperatingTime = null;
        String newContactNumber = "987-654-3210";
        savedStore.updateInfo(newContents, newOperatingTime, newContactNumber);
        Store updatedStore = storeRepository.save(savedStore);

        // then
        assertThat(updatedStore.getId()).isEqualTo(savedStore.getId());
        assertThat(updatedStore.getName()).isEqualTo(savedStore.getName());
        assertThat(updatedStore.getImagePath()).isEqualTo(store.getImagePath());
        assertThat(updatedStore.getContents()).isEqualTo(newContents);
        assertThat(updatedStore.getFloor()).isEqualTo(savedStore.getFloor());
        assertThat(updatedStore.getOperatingTime()).isEqualTo(savedStore.getOperatingTime());
        assertThat(updatedStore.getContactNumber()).isEqualTo(newContactNumber);
    }

    @Test
    @DisplayName("상점 정보 수정 성공 테스트 3 - 부분 수정")
    public void givenExistingStore_whenUpdateInfoPart_thenStoreIsUpdated() {
        // given
        Store store = Store.builder()
                .name("테스트 상점")
                .storeType("테스트 타입")
                .imagePath("테스트/이미지/경로")
                .contents("테스트 내용")
                .floor(Floor.FIRST)
                .operatingTime("09:00 ~ 18:00")
                .contactNumber("123-456-7890")
                .build();
        Store savedStore = storeRepository.save(store);

        // when
        String newContents = "새로운 내용";
        String newOperatingTime = "10:00 ~ 19:00";
        String newContactNumber = "987-654-3210";
        savedStore.updateInfo(newContents, newOperatingTime, newContactNumber);
        Store updatedStore = storeRepository.save(savedStore);

        // then
        assertThat(updatedStore.getId()).isEqualTo(savedStore.getId());
        assertThat(updatedStore.getName()).isEqualTo(savedStore.getName());
    }

    @Test
    @DisplayName("상점 삭제 성공 테스트")
    public void givenExistingStore_whenDelete_thenStoreIsDeleted() {
        // given
        Store store = Store.builder()
                .name("테스트 상점")
                .storeType("테스트 타입")
                .imagePath("테스트/이미지/경로")
                .contents("테스트 내용")
                .floor(Floor.FIRST)
                .operatingTime("09:00 ~ 18:00")
                .contactNumber("123-456-7890")
                .build();
        Store savedStore = storeRepository.save(store);

        // when
        storeRepository.delete(savedStore);

        // then
        Optional<Store> optionalStore = storeRepository.findById(savedStore.getId());
        assertThat(optionalStore.isPresent()).isFalse();
    }

    @Test
    @Disabled("동적 쿼리가 필요하여 Querydsl을 사용하여 구현함")
    @DisplayName("상점 목록 조회 성공 테스트 - (name : null)")
    public void givenSearchStoreRequest_whenFindByNameContainingOrFloorOrStoreType_thenStoreListIsFound() {
        // given
        Store store1_1 = Store.builder()
                .name("테스트 상점1_1")
                .storeType("테스트 타입1")
                .floor(Floor.FIRST)
                .build();
        Store store1_2 = Store.builder()
                .name("테스트 상점1_2")
                .storeType("테스트 타입1")
                .floor(Floor.FIRST)
                .build();
        Store store2_1 = Store.builder()
                .name("테스트 상점2_1")
                .storeType("테스트 타입2")
                .floor(Floor.SECOND)
                .build();
        Store store2_2 = Store.builder()
                .name("테스트 상점2_2")
                .storeType("테스트 타입2")
                .floor(Floor.SECOND)
                .build();
        storeRepository.save(store1_1);
        storeRepository.save(store1_2);
        storeRepository.save(store2_1);
        storeRepository.save(store2_2);

        // when
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));

        // 1. 1층 상점 목록 조회
        Page<StoreListResponse> storeList1 = storeRepository.findByNameContainingAndFloorAndStoreType(null, 1, "테스트 타입1", pageable);

        // 2. 2층 상점 목록 조회
        Page<StoreListResponse> storeList2 = storeRepository.findByNameContainingAndFloorAndStoreType(null, 2, "테스트 타입2", pageable);

        // then
        // when 1 검증
        assertThat(storeList1).isNotNull();
        assertThat(storeList1.getTotalElements()).isEqualTo(2);
        List<StoreListResponse> content1 = storeList1.getContent();
        assertThat(content1).extracting("name").containsExactly("테스트 상점1_1", "테스트 상점1_2");
        assertThat(content1).extracting("storeType").containsExactly("테스트 타입1", "테스트 타입1");
        assertThat(content1).extracting("floor").containsExactly(1, 1);

        // when 2 검증
        assertThat(storeList2).isNotNull();
        assertThat(storeList2.getTotalElements()).isEqualTo(2);
        List<StoreListResponse> content2 = storeList2.getContent();
        assertThat(content2).extracting("name").containsExactly("테스트 상점2_1", "테스트 상점2_2");
        assertThat(content2).extracting("storeType").containsExactly("테스트 타입2", "테스트 타입2");
        assertThat(content2).extracting("floor").containsExactly(2, 2);
    }

    @Test
    @Disabled("동적 쿼리가 필요하여 Querydsl을 사용하여 구현함")
    @DisplayName("상점 목록 조회 성공 테스트 - (name : not null)")
    public void givenSearchStoreRequestNameNotNull_whenFindByNameContainingOrFloorOrStoreType_thenStoreListIsFound() {
        // given
        Store store1_1 = Store.builder()
                .name("테스트 상점1_1")
                .storeType("테스트 타입1")
                .floor(Floor.FIRST)
                .build();
        Store store1_2 = Store.builder()
                .name("테스트 상점1_2")
                .storeType("테스트 타입1")
                .floor(Floor.FIRST)
                .build();
        Store store2_1 = Store.builder()
                .name("테스트 상점2_1")
                .storeType("테스트 타입2")
                .floor(Floor.SECOND)
                .build();
        Store store2_2 = Store.builder()
                .name("테스트 상점2_2")
                .storeType("테스트 타입2")
                .floor(Floor.SECOND)
                .build();
        storeRepository.save(store1_1);
        storeRepository.save(store1_2);
        storeRepository.save(store2_1);
        storeRepository.save(store2_2);

        // when
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));

        // 1. name이 "1_1"이고 1층에 있는 상점 목록 조회
        Page<StoreListResponse> storeList1 = storeRepository.findByNameContainingAndFloorAndStoreType("1_1", 1, "테스트 타입1", pageable);

        // 2. name이 "1_2"이고 2층에 있는 상점 목록 조회(검색 결과 없음)
        Page<StoreListResponse> storeList2 = storeRepository.findByNameContainingAndFloorAndStoreType("1_2", 2, "테스트 타입2", pageable);

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
