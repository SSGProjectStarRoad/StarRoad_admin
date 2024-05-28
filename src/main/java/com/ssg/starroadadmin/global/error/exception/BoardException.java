package com.ssg.starroadadmin.global.error.exception;

import com.ssg.starroadadmin.global.error.code.BoardErrorCode;
import com.ssg.starroadadmin.global.error.code.ComplexmallErrorCode;

public class BoardException extends RuntimeException {
    private final BoardErrorCode errorCode;
    private final String errorMessage;

    public BoardException(BoardErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
