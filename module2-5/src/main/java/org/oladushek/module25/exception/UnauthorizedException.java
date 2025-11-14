package org.oladushek.module25.exception;

public class UnauthorizedException extends ApiException{
    public UnauthorizedException(String message) {
        super(message, ApiErrorCode.UNAUTHORIZED);
    }
}
