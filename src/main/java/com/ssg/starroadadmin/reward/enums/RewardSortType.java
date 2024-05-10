package com.ssg.starroadadmin.reward.enums;

import org.springframework.data.domain.Sort;

public enum RewardSortType {
    NAME_ASC("name", Sort.Direction.ASC),
    NAME_DESC("name", Sort.Direction.DESC),
    CREATED_AT_ASC("createdAt", Sort.Direction.ASC),
    CREATED_AT_DESC("createdAt", Sort.Direction.DESC);

    private final String field;
    private final Sort.Direction direction;

    RewardSortType(String field, Sort.Direction direction) {
        this.field = field;
        this.direction = direction;
    }
}
