package com.rdb.refresh;

public class RefreshRequestError {

    private int errorType;
    private Exception exception;

    public RefreshRequestError(int errorType, Exception exception) {
        this.errorType = errorType;
        this.exception = exception;
    }

    public int getErrorType() {
        return errorType;
    }

    public Exception getException() {
        return exception;
    }
}
