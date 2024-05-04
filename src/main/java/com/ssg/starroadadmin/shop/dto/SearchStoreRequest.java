package com.ssg.starroadadmin.shop.dto;

import org.springframework.data.domain.Sort;

public record SearchStoreRequest(
        String storeName, // 매장 이름
        int floor, // 매장 층수
        String storeType, // 매장 타입
        Sort.Direction direction, // 매장이름(오름차순, 내림차순)
        int pageNumber, // 페이지 번호
        int pageSize // 페이지 크기
) {
}
