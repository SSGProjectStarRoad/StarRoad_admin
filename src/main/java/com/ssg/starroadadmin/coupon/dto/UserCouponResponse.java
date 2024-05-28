package com.ssg.starroadadmin.coupon.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserCouponResponse(
        Long couponHistoryId,
        Long userId,
        String userNickname,
        Long couponId,
        String couponName,
        String couponShopType,
        Boolean useStatus,
        Integer discountRate,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        LocalDate expiredAt
) {
}
