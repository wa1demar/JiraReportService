package com.swansoftwaresolutions.jirareport.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class UserDto implements Serializable {

    @NotNull(message = "Username cannot be empty")
    private String username;

    private String fullName;

    @NotNull(message = "Email cannot be empty")
    private String email;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
