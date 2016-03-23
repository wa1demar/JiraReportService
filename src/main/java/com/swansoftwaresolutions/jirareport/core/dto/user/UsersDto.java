package com.swansoftwaresolutions.jirareport.core.dto.user;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class UsersDto implements Serializable {

   List<UserDto> users;

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
