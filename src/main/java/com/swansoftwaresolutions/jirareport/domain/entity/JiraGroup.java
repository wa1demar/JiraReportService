package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */

@Entity
@Table(name = "jira_groups")
public class JiraGroup  implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "jira_users_groups", joinColumns = {
            @JoinColumn(name = "group_name") },
            inverseJoinColumns = { @JoinColumn(name = "user_login") })
    private List<JiraUser> users = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JiraUser> getUsers() {
        return users;
    }

    public void setUsers(List<JiraUser> users) {
        this.users = users;
    }
}
