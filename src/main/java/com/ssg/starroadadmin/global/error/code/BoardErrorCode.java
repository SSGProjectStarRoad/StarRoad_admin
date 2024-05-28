package com.ssg.starroadadmin.global.error.code;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BoardErrorCode {
    BOARD_NOT_FOUND("해당 게시글은 존재하지 않습니다."),
    BOARD_IMAGE_NOT_FOUND("해당 이미지는 존재하지 않습니다."),;

    private final String description;
}
