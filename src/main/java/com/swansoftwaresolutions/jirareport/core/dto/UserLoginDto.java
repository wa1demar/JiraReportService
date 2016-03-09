package com.swansoftwaresolutions.jirareport.core.dto;

import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class UserLoginDto implements Serializable {

    private UserDto user;
    private String token;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
