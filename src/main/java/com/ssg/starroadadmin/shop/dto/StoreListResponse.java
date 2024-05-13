package com.ssg.starroadadmin.shop.dto;

import com.ssg.starroadadmin.shop.enums.Floor;

public record StoreListResponse(
        Long id,
        String name,
        String storeType,
        String imagePath,
        Floor floor,
        String contactNumber
) {
}