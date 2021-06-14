package com.ecirstea.user.exception;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {
    private HttpStatus code;
    private String errorField;

    public UserException(HttpStatus badRequest, String message, String errorField) {
        super(message);
        this.code = badRequest;
        this.errorField = errorField;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getErrorField() {
        return errorField;
    }

    public void setErrorField(String errorField) {
        this.errorField = errorField;
    }
}
