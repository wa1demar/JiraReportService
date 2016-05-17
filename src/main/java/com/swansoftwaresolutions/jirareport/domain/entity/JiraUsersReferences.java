package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;

/**
 * @author Vladimir Martynyuk
 */

@Entity
@Table(name = "jira_users_references")
public class JiraUsersReferences {

    private Long id;
    private JiraUser user;
    private ResourceColumn column;
    private Project project;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jira_user_login", nullable = false)
    public JiraUser getUser() {
        return user;
    }

    public void setUser(JiraUser user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assygnment_type_id")
    public ResourceColumn getColumn() {
        return column;
    }

    public void setColumn(ResourceColumn column) {
        this.column = column;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = true)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
