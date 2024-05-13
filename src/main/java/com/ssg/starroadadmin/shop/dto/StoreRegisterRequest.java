package com.ssg.starroadadmin.shop.dto;

import com.ssg.starroadadmin.shop.enums.Floor;
import lombok.Builder;

@Builder
public record StoreRegisterRequest(
        String storeName,
        String storeType,
        Floor storeFloor,
        Long StoreManagerId
) {
}
