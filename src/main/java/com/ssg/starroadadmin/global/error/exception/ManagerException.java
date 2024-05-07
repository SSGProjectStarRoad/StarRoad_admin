package com.ssg.starroadadmin.global.error.exception;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.ShopErrorCode;

public class ManagerException  extends RuntimeException {
    private final ManagerErrorCode errorCode;
    private final String errorMessage;

    public ManagerException(ManagerErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
