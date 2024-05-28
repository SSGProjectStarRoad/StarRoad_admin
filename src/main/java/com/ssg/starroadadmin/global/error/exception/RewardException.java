package com.ssg.starroadadmin.global.error.exception;

import com.ssg.starroadadmin.global.error.code.RewardErrorCode;

public class RewardException extends RuntimeException {
    private final RewardErrorCode errorCode;
    private final String errorMessage;

    public RewardException(RewardErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
