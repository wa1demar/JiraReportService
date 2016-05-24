package com.swansoftwaresolutions.jirareport.domain.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "jira_users")
public class JiraUser implements Serializable {
    private String email;
    private String login;
    private String fullName;
    private Long jiraUserId;
    private String description;
    private List<Report> reports = new ArrayList<>();
    private List<JiraGroup> groups = new ArrayList<>();
    private Location location;
    private Position position;
    private List<Technology> technologies = new ArrayList<>();
    private List<Attachment> attachments = new ArrayList<>();
    private String avatar;
    private int resourceOrder;
    private List<JiraUsersReferences> userReferences = new ArrayList<>();

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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    @Cascade(CascadeType.MERGE)
    public List<JiraGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<JiraGroup> groups) {
        this.groups = groups;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", nullable = true)
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @OrderBy("id ASC")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jiraUser")
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column(name = "resource_order")
    public int getResourceOrder() {
        return resourceOrder;
    }

    public void setResourceOrder(int resourceOrder) {
        this.resourceOrder = resourceOrder;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public List<JiraUsersReferences> getUserReferences() {
        return userReferences;
    }

    public void setUserReferences(List<JiraUsersReferences> userReferences) {
        this.userReferences = userReferences;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = true)
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
