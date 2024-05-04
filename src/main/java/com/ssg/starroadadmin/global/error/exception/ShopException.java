package com.ssg.starroadadmin.global.error.exception;

import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import lombok.Getter;

@Getter
public class ShopException extends RuntimeException {
    private final ShopErrorCode errorCode;
    private final String errorMessage;

    public ShopException(ShopErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
