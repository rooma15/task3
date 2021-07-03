package com.epam.esm.web.exception;

public class ResponseError {
    private final String errorMessage;
    private final int errorCode;

    public ResponseError(String message, int code) {
        this.errorMessage = message;
        this.errorCode = code;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String toString() {
        return "ResponseError{" +
                "errorMessage='" + errorMessage + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
