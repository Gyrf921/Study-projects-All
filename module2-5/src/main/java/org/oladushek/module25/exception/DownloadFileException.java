package org.oladushek.module25.exception;

public class DownloadFileException extends ApiException {
    public DownloadFileException(String message) {
        super(message, ApiErrorCode.DOWNLOAD_ERROR);
    }
}
