package com.ecirstea.user.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class UserFeedback {
    @ApiModelProperty()
    private String username;
    @ApiModelProperty(position = 1)
    private String message;
    @ApiModelProperty(position = 2)
    private String date;

    public UserFeedback() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Username " + username+
                " sent " + "\""+ message  +
                " \" on " + date;
    }
}
