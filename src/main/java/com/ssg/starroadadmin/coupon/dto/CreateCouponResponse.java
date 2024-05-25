package com.ssg.starroadadmin.coupon.dto;

import com.ssg.starroadadmin.coupon.entity.Coupon;
import lombok.Builder;

@Builder
public record CreateCouponResponse(
        String couponName,
        int discountRate,
        int discountAmount,
        int maxDiscountAmount,
        int minPurchaseAmount,
        String mallName,
        String status
) {
    public static CreateCouponResponse from(Coupon save) {
        return CreateCouponResponse.builder()
                .couponName(save.getName())
                .discountRate(save.getDiscountRate())
                .discountAmount(save.getDiscountAmount())
                .maxDiscountAmount(save.getMaxAmount())
                .minPurchaseAmount(save.getMinAmount())
                .mallName(save.getComplexShoppingmall())
                .status(save.getStatus())
                .build();
    }
}