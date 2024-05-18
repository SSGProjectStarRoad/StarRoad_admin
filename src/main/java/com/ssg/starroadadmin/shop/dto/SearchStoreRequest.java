package com.ssg.starroadadmin.shop.dto;

import com.ssg.starroadadmin.shop.enums.Floor;
import com.ssg.starroadadmin.shop.enums.StoreSortType;
import com.ssg.starroadadmin.shop.enums.StoreType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

public record SearchStoreRequest(
        String storeName, // 매장 이름
        Floor floor, // 매장 층수
        String storeType, // 매장 타입
        StoreSortType sortType // 매장이름(오름차순, 내림차순)
) {
}
