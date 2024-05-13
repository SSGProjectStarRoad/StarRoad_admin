package com.ssg.starroadadmin.review.enums;

import org.springframework.data.domain.Sort;

public enum ReviewSortType {
    CREATED_AT_ASC("createdAt", Sort.Direction.ASC),
    CREATED_AT_DESC("createdAt", Sort.Direction.DESC),
    LIKE_COUNT_ASC("likeCount", Sort.Direction.ASC),
    LIKE_COUNT_DESC("likeCount", Sort.Direction.DESC);

    private final String field;
    private final Sort.Direction direction;

    ReviewSortType(String field, Sort.Direction direction) {
        this.field = field;
        this.direction = direction;
    }
}
