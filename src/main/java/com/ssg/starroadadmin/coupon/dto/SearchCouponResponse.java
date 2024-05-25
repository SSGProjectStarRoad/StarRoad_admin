package com.ssg.starroadadmin.coupon.dto;

import com.ssg.starroadadmin.coupon.entity.Coupon;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SearchCouponResponse(
        Long couponId,
        String couponName,
        String couponShopType,
        int couponDiscountAmount,
        int couponDiscountRate,
        int couponMinAmount,
        int couponMaxAmount,
        String couponStatus,
        String couponComplexShoppingmallName,
        LocalDateTime couponCreatedAt,
        LocalDateTime couponModifiedAt

) {
    public static SearchCouponResponse from(Coupon coupon) {
        return SearchCouponResponse.builder()
                .couponId(coupon.getId())
                .couponName(coupon.getName())
                .couponShopType(coupon.getShopType())
                .couponDiscountAmount(coupon.getDiscountAmount())
                .couponDiscountRate(coupon.getDiscountRate())
                .couponMinAmount(coupon.getMinAmount())
                .couponMaxAmount(coupon.getMaxAmount())
                .couponStatus(coupon.getStatus())
                .couponComplexShoppingmallName(coupon.getComplexShoppingmall())
                .couponCreatedAt(coupon.getCreatedAt())
                .couponModifiedAt(coupon.getModifiedAt())
                .build();
    }
}
