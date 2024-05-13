package com.ssg.starroadadmin.shop.repository;

import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.enums.Floor;
import com.ssg.starroadadmin.shop.enums.StoreSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface StoreRepositoryCustom {
    Page<StoreListResponse> findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
            Long complexShoppingmallId, String name, Floor floor, String storeType, StoreSortType sortType, Pageable pageable);
}
