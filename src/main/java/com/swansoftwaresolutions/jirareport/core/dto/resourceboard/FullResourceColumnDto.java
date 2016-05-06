package com.swansoftwaresolutions.jirareport.core.dto.resourceboard;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullResourceUserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class FullResourceColumnDto {

    private Long id;
    private String name;
    private String color;
    private boolean fixed;
    private int priority;
    private int sortPosition;
    private List<FullResourceUserDto> users = new ArrayList<>();

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

    public List<FullResourceUserDto> getUsers() {
        return users;
    }

    public void setUsers(List<FullResourceUserDto> users) {
        this.users = users;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getSortPosition() {
        return sortPosition;
    }

    public void setSortPosition(int sortPosition) {
        this.sortPosition = sortPosition;
    }
}
