package com.ecirstea.user.security;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @ApiModelProperty()
    private String username;
    @ApiModelProperty(position = 1)
    private String password;

    // need default constructor for JSON Parsing
    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    private void setPassword(String password) {
        this.password = password;
    }
}