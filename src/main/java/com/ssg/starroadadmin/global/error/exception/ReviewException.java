package com.ssg.starroadadmin.global.error.exception;

import com.ssg.starroadadmin.global.error.code.ReviewErrorCode;

public class ReviewException extends RuntimeException {
    private final ReviewErrorCode reviewErrorCode;
    private final String errorMessage;

    public ReviewException(ReviewErrorCode reviewErrorCode) {
        super(reviewErrorCode.getDescription());
        this.reviewErrorCode = reviewErrorCode;
        this.errorMessage = reviewErrorCode.getDescription();
    }
}
