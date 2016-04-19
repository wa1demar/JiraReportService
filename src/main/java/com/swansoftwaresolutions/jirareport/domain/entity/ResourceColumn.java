package com.swansoftwaresolutions.jirareport.domain.entity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "resource_columns")
public class ResourceColumn implements Serializable {

    private Long id;
    private String name;
    private String color;
    private boolean fixed;
    private List<JiraUser> users = new ArrayList<>();

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "fixed")
    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "column")
    public List<JiraUser> getUsers() {
        return users;
    }

    public void setUsers(List<JiraUser> users) {
        this.users = users;
    }
}
