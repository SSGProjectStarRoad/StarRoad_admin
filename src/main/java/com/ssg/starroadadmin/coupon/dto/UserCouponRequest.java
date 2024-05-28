package com.ssg.starroadadmin.coupon.dto;

import com.ssg.starroadadmin.coupon.enums.CouponSortType;

import java.time.LocalDate;

public record UserCouponRequest(
        LocalDate startDate,
        LocalDate endDate,
        CouponSortType sortType,
        String usageStatus,
        String userNickname,
        String couponName
) {
}
