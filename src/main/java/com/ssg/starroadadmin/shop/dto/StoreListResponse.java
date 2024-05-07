package com.ssg.starroadadmin.shop.dto;

public record StoreListResponse(
        Long id,
        String name,
        String storeType,
        String imagePath,
        int floor,
        String contactNumber
) {
}