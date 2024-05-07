package com.ssg.starroadadmin.global.error.code;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ShopErrorCode {
    SHOPPINGMALL_NOT_FOUND("해당 쇼핑몰은 존재하지 않습니다."),
    STORE_NOT_FOUND("해당 매장은 존재하지 않습니다."),
    ACCESS_DENIED("접근 권한이 없습니다.");

    private final String description;
}
