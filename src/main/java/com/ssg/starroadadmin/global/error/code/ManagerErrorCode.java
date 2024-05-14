package com.ssg.starroadadmin.global.error.code;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ManagerErrorCode {
    STORE_MANAGER_NOT_FOUND("해당 매장 관리자는 존재하지 않습니다."),
    ACCESS_DENIED("접근할 수 없는 항목입니다."),
    MANAGER_NOT_FOUND("해당 관리자는 존재하지 않습니다.");

    private final String description;
}
