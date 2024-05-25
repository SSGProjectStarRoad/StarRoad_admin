package com.ssg.starroadadmin.global.error.exception;

import com.ssg.starroadadmin.global.error.code.CouponErrorCode;

public class CouponException extends RuntimeException {

    private final CouponErrorCode couponErrorCode;
    private final String errorMessage;
    public CouponException(CouponErrorCode couponErrorCode) {
        super(couponErrorCode.getDescription());
        this.couponErrorCode = couponErrorCode;
        this.errorMessage = couponErrorCode.getDescription();
    }
}
