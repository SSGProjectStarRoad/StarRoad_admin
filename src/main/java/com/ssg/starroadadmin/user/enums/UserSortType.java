package com.ssg.starroadadmin.user.enums;

import lombok.Getter;

@Getter
public enum UserSortType {
    NAME_ASC("이름 오름차순"),
    NAME_DESC("이름 내림차순"),
    REVIEW_COUNT_ASC("리뷰 적은 순"),
    REVIEW_COUNT_DESC("리뷰 많은 순"),
    FOLLOWER_COUNT_ASC("팔로워 적은 순"),
    FOLLOWER_COUNT_DESC("팔로워 많은 순"),
    CREATED_AT_ASC("가입일 오름차순"),
    CREATED_AT_DESC("가입일 내림차순");

    private final String discription;

    UserSortType(String discription) {
        this.discription = discription;
    }
}
