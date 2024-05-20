package com.ssg.starroadadmin.global.error.exception;

import com.ssg.starroadadmin.global.error.code.FollowErrorCode;
import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;

public class FollowException  extends RuntimeException {
    private final FollowErrorCode errorCode;
    private final String errorMessage;

    public FollowException(FollowErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
