package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "positions")
public class Position {

    private Long id;
    private String name;
    private List<JiraUser> users = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "position")
    public List<JiraUser> getUsers() {
        return users;
    }

    public void setUsers(List<JiraUser> users) {
        this.users = users;
    }

    @Column(name = "title")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
