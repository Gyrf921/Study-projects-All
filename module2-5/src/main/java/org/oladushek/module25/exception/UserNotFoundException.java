package org.oladushek.module25.exception;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException(String message) {
        super(message, ApiErrorCode.RESOURCE_NOT_FOUND);
    }
}
