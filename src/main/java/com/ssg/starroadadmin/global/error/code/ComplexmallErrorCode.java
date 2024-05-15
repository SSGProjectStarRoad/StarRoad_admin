package com.ssg.starroadadmin.global.error.code;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ComplexmallErrorCode {
    COMPLEXMALL_NOT_FOUND("해당 복합몰은 존재하지 않습니다.");

    private final String description;
}
