package com.ssg.starroadadmin.shop.dto;

import lombok.Builder;

@Builder
public record StoreModifyRequest(
    String contents,
    String operatingTime,
    String contactNumber
) {
}
