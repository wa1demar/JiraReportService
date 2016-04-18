package com.swansoftwaresolutions.jirareport.core.dto.resourceboard;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class FullResourceColumnDto {

    private Long id;
    private String name;
    private String color;
    private List<ResourceUserDto> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<ResourceUserDto> getUsers() {
        return users;
    }

    public void setUsers(List<ResourceUserDto> users) {
        this.users = users;
    }
}
