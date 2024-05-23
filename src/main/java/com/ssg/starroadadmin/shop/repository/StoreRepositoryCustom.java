package com.ssg.starroadadmin.shop.repository;

import com.ssg.starroadadmin.review.enums.ConfidenceType;
import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.enums.Floor;
import com.ssg.starroadadmin.shop.enums.StoreSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StoreRepositoryCustom {
    Page<StoreListResponse> findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
            Long complexShoppingmallId, String name, Floor floor, String storeType, StoreSortType sortType, Pageable pageable);

    Optional<ConfidenceType> findStoreConfidence(Long storeId);
}
