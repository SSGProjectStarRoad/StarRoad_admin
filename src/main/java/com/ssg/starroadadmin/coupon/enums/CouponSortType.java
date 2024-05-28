package com.ssg.starroadadmin.coupon.enums;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum CouponSortType {
    NAME_ASC("이름순"),
    NAME_DESC("이름 역순"),
    USER_NAME_ASC("유저 이름순"),
    USER_NAME_DESC("유저 이름 역순"),
    CREATED_AT_ASC("오래된순"),
    CREATED_AT_DESC("새로운순");

    private final String field;

    CouponSortType(String field) {
        this.field = field;
    }
}
