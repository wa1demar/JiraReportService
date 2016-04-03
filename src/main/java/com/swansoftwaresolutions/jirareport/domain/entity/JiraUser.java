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

    @Column(name = "email")
    private String email;

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "jira_user_id")
    private Long jiraUserId;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "admins")
    private List<Report> reports = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.MERGE)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 10)
    private List<JiraGroup> groups = new ArrayList<>();


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getJiraUserId() {
        return jiraUserId;
    }

    public void setJiraUserId(Long jiraUserId) {
        this.jiraUserId = jiraUserId;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public List<JiraGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<JiraGroup> groups) {
        this.groups = groups;
    }
}
