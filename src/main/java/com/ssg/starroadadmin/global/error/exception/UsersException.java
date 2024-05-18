package com.ssg.starroadadmin.global.error.exception;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.UserErrorCode;

public class UsersException extends RuntimeException {
    private final UserErrorCode errorCode;
    private final String errorMessage;

    public UsersException(UserErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
