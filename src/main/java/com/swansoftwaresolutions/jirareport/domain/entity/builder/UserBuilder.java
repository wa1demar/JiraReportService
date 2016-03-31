package com.swansoftwaresolutions.jirareport.domain.entity.builder;

import com.swansoftwaresolutions.jirareport.domain.entity.Role;
import com.swansoftwaresolutions.jirareport.domain.entity.User;
import com.swansoftwaresolutions.jirareport.domain.enums.UserStatus;

import java.util.Set;

/**
 * @author Vladimir Martynyuk
 */
public class UserBuilder {

    private User user;

    public UserBuilder() {
        this.user = new User();
    }


    public UserBuilder id(Long id) {
        user.setId(id);
        return this;
    }


    public UserBuilder username(String userName) {
        user.setUsername(userName);
        return this;
    }

    public UserBuilder fullName(String fullName) {
        user.setFullName(fullName);
        return this;
    }

    public UserBuilder email(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder password(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder status(UserStatus status) {
        user.setStatus(status);
        return this;
    }

    public UserBuilder roles(Set<Role> roles) {
        user.setRoles(roles);
        return this;
    }

    public User build() {
        return user;
    }
}
