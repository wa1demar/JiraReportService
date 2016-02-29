package com.swansoftwaresolutions.jirareport.core.entity;

import javax.persistence.*;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "jira_projects")
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "jira_id")
    private Long jiraId;

    @Column(name = "key")
    private String key;

    @Column(name = "name")
    private String name;

    public Object getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setJiraId(Long jiraId) {
        this.jiraId = jiraId;
    }

    public Long getJiraId() {
        return jiraId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
