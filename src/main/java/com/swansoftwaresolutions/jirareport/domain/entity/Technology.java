package com.swansoftwaresolutions.jirareport.domain.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import org.hibernate.annotations.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "technologies")
public class Technology implements Serializable {
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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "jira_users_technologies", joinColumns = {
            @JoinColumn(name = "technology_id")},
            inverseJoinColumns = {@JoinColumn(name = "jira_user_login")})
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DETACH})
    public List<JiraUser> getUsers() {
        return users;
    }

    public void setUsers(List<JiraUser> users) {
        this.users = users;
    }
}
