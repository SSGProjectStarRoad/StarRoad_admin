package com.ssg.starroadadmin.global.error.exception;

import com.ssg.starroadadmin.global.error.code.ComplexmallErrorCode;

public class ComplexmallException extends RuntimeException {
    private final ComplexmallErrorCode errorCode;
    private final String errorMessage;

    public ComplexmallException(ComplexmallErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
