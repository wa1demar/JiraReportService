package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "attachments")
public class Attachment {

    private Long id;
    private String title;
    private String url;
    private JiraUser jiraUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jira_user_login", nullable = false)
    public JiraUser getJiraUser() {
        return jiraUser;
    }

    public void setJiraUser(JiraUser jiraUser) {
        this.jiraUser = jiraUser;
    }
}
