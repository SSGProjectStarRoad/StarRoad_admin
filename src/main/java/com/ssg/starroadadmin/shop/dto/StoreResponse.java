package com.ssg.starroadadmin.shop.dto;

import com.ssg.starroadadmin.review.dto.ReviewDetails;
import com.ssg.starroadadmin.review.dto.ReviewListResponse;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.enums.Floor;
import com.ssg.starroadadmin.user.entity.Manager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record StoreResponse(
        Long id,
        String name, // 매장명
        String storeType, // 매장 타입
        String imagePath, // 매장 이미지
        String contents, // 매장 설명
        String operatingTime, // 운영시간
        String contactNumber, // 연락처
        Floor floor, // 층수
        String storeGuideMap, // 매장 안내도
        Long managerId, // 매장 관리자 아이디
        String managerName, // 매장 관리자 이름
        String managerUsername, // 매장 관리자 아이디
        String businessNumber, // 사업자 등록번호
        LocalDate managerCreatedAt // 생성일
) {
    public static StoreResponse from(Manager manager, Store store) {
        return new StoreResponse(
                store.getId(),
                store.getName(),
                store.getStoreType(),
                store.getImagePath(),
                store.getContents(),
                store.getOperatingTime(),
                store.getContactNumber(),
                store.getFloor(),
                store.getStoreGuideMap(),
                manager.getId(),
                manager.getName(),
                manager.getUsername(),
                BusinessNumberFormat(manager.getBusinessNumber()),
                manager.getCreatedAt().toLocalDate()
        );
    }

    private static String BusinessNumberFormat(String businessNumber) {
        return businessNumber.substring(0, 3) + "-" + businessNumber.substring(3, 5) + "-" + businessNumber.substring(5, businessNumber.length());
    }
}
