package com.ssg.starroadadmin.shop.enums;

import lombok.Getter;

@Getter
public enum StoreType {
    RESTAURANT_AND_CAFE("식당&카페"),
    LIFESTYLE("라이프스타일"),
    CLOTHING("의류"),
    BEAUTY("뷰티"),
    KIDS("키즈"),
    ENTERTAINMENT("엔터테인먼트"),
    INTERIOR("홈퍼니싱"),
    SUPERMARKET("마트");

    private final String name;

    StoreType(String name) {
        this.name = name;
    }
}
