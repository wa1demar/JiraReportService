package com.swansoftwaresolutions.jirareport.core.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swansoftwaresolutions.jirareport.domain.enums.UserStatus;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class UserDto implements Serializable {

    private Long id;

    @NotNull(message = "Username cannot be empty")
    private String username;

    private String fullName;

    @NotNull(message = "Email cannot be empty")
    @Email
    private String email;

    @NotNull(message = "Status cannot be empty")
    private UserStatus status;

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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
