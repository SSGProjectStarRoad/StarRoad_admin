package com.ssg.starroadadmin.shop.enums;

import org.springframework.data.domain.Sort;

public enum StoreSortType {
    NULL("name", Sort.Direction.ASC),
    NAME_ASC("name", Sort.Direction.ASC),
    NAME_DESC("name", Sort.Direction.DESC),
    FLOOR_ASC("floor", Sort.Direction.ASC),
    FLOOR_DESC("floor", Sort.Direction.DESC),
    CREATED_AT_ASC("createdAt", Sort.Direction.ASC),
    CREATED_AT_DESC("createdAt", Sort.Direction.DESC);

    private final String field;
    private final Sort.Direction direction;

    StoreSortType(String field, Sort.Direction direction) {
        this.field = field;
        this.direction = direction;
    }
}
