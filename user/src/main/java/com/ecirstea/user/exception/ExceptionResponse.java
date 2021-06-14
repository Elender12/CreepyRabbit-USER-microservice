package com.ecirstea.user.exception;

import java.util.Date;

public class ExceptionResponse {
    private Date timestamp;
    private int status;
    private String error;
    private String field;
    private String problem;
    private String message;
    private String path;

    public ExceptionResponse(Date timestamp, int status, String error, String field, String problem, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.field = field;
        this.problem = problem;
        this.message = message;
        this.path = path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", field='" + field + '\'' +
                ", problem='" + problem + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}