package com.ssg.starroadadmin.global.error.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum FollowErrorCode {
    FOLLOW_LIST_NOT_FOUND("팔로우 리스트가 존재하지 않습니다.");

    private final String description;
}
