package com.swansoftwaresolutions.jirareport.domain.entity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.CascadeType.*;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "jira_users")
public class JiraUser {
    private String email;
    private String login;
    private String fullName;
    private Long jiraUserId;
    private String description;
    private List<Report> reports = new ArrayList<>();
    private List<JiraGroup> groups = new ArrayList<>();
    private Location location;
    private List<Technology> technologies = new ArrayList<>();

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Id
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "jira_user_id")
    public Long getJiraUserId() {
        return jiraUserId;
    }

    public void setJiraUserId(Long jiraUserId) {
        this.jiraUserId = jiraUserId;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "admins")
    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 10)
    public List<JiraGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<JiraGroup> groups) {
        this.groups = groups;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = true)
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
