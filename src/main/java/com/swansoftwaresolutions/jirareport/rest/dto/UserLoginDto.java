package com.swansoftwaresolutions.jirareport.rest.dto;

/**
 * @author Vladimir Martynyuk
 */
public class UserLoginDto {

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
