package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "locations")
public class Location implements Serializable {
    private Long id;
    private String name;
    private List<JiraUser> jiraUsers;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    public List<JiraUser> getJiraUsers() {
        return jiraUsers;
    }

    public void setJiraUsers(List<JiraUser> jiraUsers) {
        this.jiraUsers = jiraUsers;
    }
}
