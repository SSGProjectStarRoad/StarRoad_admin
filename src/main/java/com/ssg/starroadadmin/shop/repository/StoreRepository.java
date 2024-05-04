package com.ssg.starroadadmin.shop.repository;

import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<StoreListResponse> findByNameContainingOrFloorOrStoreType(String name, int floor, String storeType, Pageable pageable);
}
