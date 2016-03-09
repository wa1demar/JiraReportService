package com.swansoftwaresolutions.jirareport.core.dto;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class JiraUsersDto {

    private List<JiraUserDto> users;

    public List<JiraUserDto> getUsers() {
        return users;
    }

    public void setUsers(List<JiraUserDto> users) {
        this.users = users;
    }
}
