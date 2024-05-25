package com.ssg.starroadadmin.coupon.dto;

import com.ssg.starroadadmin.coupon.entity.Coupon;

public record CreateCouponRequest(
        String couponName,
        int discountRate,
        int discountAmount,
        int maxDiscountAmount,
        int minPurchaseAmount,
        String status
) {

    public Coupon toEntity(String mallName) {
        return Coupon.builder()
                .name(couponName)
                .discountRate(discountRate)
                .discountAmount(discountAmount)
                .maxAmount(maxDiscountAmount)
                .minAmount(minPurchaseAmount)
                .complexShoppingmall(mallName)
                .status(status)
                .build();
    }
}
