package org.oladushek.module25.exception;

public class UploadFileException extends ApiException {
    public UploadFileException(String message) {
        super(message, ApiErrorCode.UPLOAD_ERROR);
    }
}
