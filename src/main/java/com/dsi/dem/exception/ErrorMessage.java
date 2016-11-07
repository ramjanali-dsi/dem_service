package com.dsi.dem.exception;

/**
 * Created by sabbir on 6/20/16.
 */
public class ErrorMessage {

    private String errorCode;
    private String errorType;
    private String errorMessage;
    //private ErrorContext errorContext;

    public ErrorMessage(String errorCode, String errorMessage, String errorType) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /*public ErrorContext getErrorContext() {
        return errorContext;
    }

    public void setErrorContext(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }*/
}
