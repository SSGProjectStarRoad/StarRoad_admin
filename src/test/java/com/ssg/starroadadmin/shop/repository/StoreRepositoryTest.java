package com.ssg.starroadadmin.shop.repository;
import com.ssg.starroadadmin.shop.entity.Store;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
                .floor(1)
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
                .floor(1)
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
                .floor(1)
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
                .floor(2)
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
                .floor(1)
                .operatingTime("09:00 ~ 18:00")
                .contactNumber("123-456-7890")
                .build();
        Store savedStore = storeRepository.save(store);

        // when
        String newImagePath = "새로운/이미지/경로";
        String newContents = "새로운 내용";
        int newFloor = 0;
        String newOperatingTime = null;
        String newContactNumber = "987-654-3210";
        savedStore.updateInfo(newImagePath, newContents, newFloor, newOperatingTime, newContactNumber);
        Store updatedStore = storeRepository.save(savedStore);

        // then
        assertThat(updatedStore.getId()).isEqualTo(savedStore.getId());
        assertThat(updatedStore.getName()).isEqualTo(savedStore.getName());
        assertThat(updatedStore.getImagePath()).isEqualTo(newImagePath);
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
                .floor(1)
                .operatingTime("09:00 ~ 18:00")
                .contactNumber("123-456-7890")
                .build();
        Store savedStore = storeRepository.save(store);

        // when
        String newImagePath = "새로운/이미지/경로";
        String newContents = "새로운 내용";
        int newFloor = 2;
        String newOperatingTime = "10:00 ~ 19:00";
        String newContactNumber = "987-654-3210";
        savedStore.updateInfo(newImagePath, newContents, newFloor, newOperatingTime, newContactNumber);
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
                .floor(1)
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
}
