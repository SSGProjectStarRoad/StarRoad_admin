package com.ssg.starroadadmin.review.enums;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum ReviewSortType {
    CREATED_AT_ASC("오래된 리뷰 순","createdAt", Sort.Direction.ASC),
    CREATED_AT_DESC("새로운 리뷰 순","createdAt", Sort.Direction.DESC),
    LIKE_COUNT_ASC("좋아요 적은 순","likeCount", Sort.Direction.ASC),
    LIKE_COUNT_DESC("좋아요 많은 순","likeCount", Sort.Direction.DESC);

    private final String discription;
    private final String field;
    private final Sort.Direction direction;

    ReviewSortType(String discription, String field, Sort.Direction direction) {
        this.discription = discription;
        this.field = field;
        this.direction = direction;
    }
}
