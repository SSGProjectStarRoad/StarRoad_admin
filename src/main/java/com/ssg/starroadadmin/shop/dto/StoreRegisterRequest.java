package com.ssg.starroadadmin.shop.dto;

import lombok.Builder;

@Builder
public record StoreRegisterRequest(
        String storeName,
        String storeType,
        int storeFloor,
        Long StoreManagerId
) {
}
