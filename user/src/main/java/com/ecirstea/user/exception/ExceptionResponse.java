package com.ecirstea.user.exception;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ExceptionResponse {
    @ApiModelProperty()
    private long timestamp;
    @ApiModelProperty(position = 1)
    private int status;
    @ApiModelProperty(position = 2)
    private String message;

    public ExceptionResponse(long timestamp, int status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
