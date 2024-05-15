package com.ssg.starroadadmin.shop.dto;

import com.ssg.starroadadmin.shop.enums.Floor;

public record StoreListResponse(
        Long id,
        String name, // 매장명
        String storeType, // 매장 타입
        String imagePath, // 매장 이미지
        Floor floor, // 층수
        String contactNumber, // 연락처
        int reviewCount // 리뷰 수
) {
}