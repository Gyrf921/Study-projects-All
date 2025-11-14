package org.oladushek.module25.exception;

import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException{

    protected ApiErrorCode errorCode;

    public ApiException(String message, ApiErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
