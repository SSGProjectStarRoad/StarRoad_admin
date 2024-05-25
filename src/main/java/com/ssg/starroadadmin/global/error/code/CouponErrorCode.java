package com.ssg.starroadadmin.global.error.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CouponErrorCode {
    COUPON_NOT_FOUND("쿠폰이 존재하지 않습니다."),
    COUPON_ALREADY_EXIST("이미 존재하는 쿠폰입니다."),
    COUPON_NOT_AVAILABLE("사용할 수 없는 쿠폰입니다."),
    COUPON_EXPIRED("쿠폰이 만료되었습니다."),
    COUPON_NOT_VALID("유효하지 않은 쿠폰입니다."),
    COUPON_NOT_DELETE("쿠폰을 삭제할 수 없습니다."),
    COUPON_NOT_UPDATE("쿠폰을 수정할 수 없습니다."),
    COUPON_NOT_CREATE("쿠폰을 생성할 수 없습니다.");

    private final String description;

}
