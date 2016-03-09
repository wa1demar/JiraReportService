package com.swansoftwaresolutions.jirareport.domain.entity;

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

    public Long getId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (!jiraId.equals(project.jiraId)) return false;
        return key.equals(project.key);

    }

    @Override
    public int hashCode() {
        int result = jiraId.hashCode();
        result = 31 * result + key.hashCode();
        return result;
    }
}
