package com.ssg.starroadadmin.coupon.dto;

import com.ssg.starroadadmin.coupon.entity.Coupon;

import java.time.LocalDate;

public record CreateCouponRequest(
        String modalCouponName,
        int modalCouponDiscountRate,
        int modalCouponDiscountAmount,
        int modalCouponMaxAmount,
        int modalCouponMinAmount,
        LocalDate modalCouponExpiredAt,
        String modalCouponShopType,
        String modalCouponStatus
) {

    public Coupon toEntity(String mallName) {
        return Coupon.builder()
                .name(modalCouponName)
                .discountRate(modalCouponDiscountRate)
                .discountAmount(modalCouponDiscountAmount)
                .maxAmount(modalCouponMaxAmount)
                .minAmount(modalCouponMinAmount)
                .expiredAt(modalCouponExpiredAt)
                .shopType(modalCouponShopType)
                .status(modalCouponStatus)
                .complexShoppingmall(mallName)
                .build();
    }
}
