package com.swansoftwaresolutions.jirareport.core.dto.resourceboard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class FullResourceColumnDtoFiltered {

    private Long id;
    private String name;
    private String color;
    private boolean fixed;
    private int priority;
    private List<FullResourceUserDtoFiltred> users = new ArrayList<>();

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

    public List<FullResourceUserDtoFiltred> getUsers() {
        return users;
    }

    public void setUsers(List<FullResourceUserDtoFiltred> users) {
        this.users = users;
    }
}
