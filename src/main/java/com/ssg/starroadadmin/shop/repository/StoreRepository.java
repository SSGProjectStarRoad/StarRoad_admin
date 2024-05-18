package com.ssg.starroadadmin.shop.repository;

import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<StoreListResponse> findByNameContainingAndFloorAndStoreType(String name, int floor, String storeType, Pageable pageable);
    List<StoreListResponse> findByNameAndStoreType(String name, String storeType);

    Optional<Store> findByIdAndManagerId(Long storeId, Long managerId);

    Optional<Store> findByIdAndComplexShoppingmallManagerId(Long storeId, Long managerId);
}
