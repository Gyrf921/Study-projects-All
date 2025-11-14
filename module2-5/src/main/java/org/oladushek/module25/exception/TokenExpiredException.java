package org.oladushek.module25.exception;

public class TokenExpiredException extends ApiException {
    public TokenExpiredException(String message) {
        super(message, ApiErrorCode.TOKEN_EXPIRED);
    }
}
