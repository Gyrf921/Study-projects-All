package org.oladushek.module25.exception;

public class AuthException extends ApiException {
    public AuthException(String message, ApiErrorCode errorCode) {
        super(message, errorCode);
    }
}
