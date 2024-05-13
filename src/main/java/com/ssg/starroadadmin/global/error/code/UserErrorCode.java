package com.ssg.starroadadmin.global.error.code;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserErrorCode {
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    ACCESS_DENIED("접근할 수 없는 항목입니다.");

    private final String description;
}
