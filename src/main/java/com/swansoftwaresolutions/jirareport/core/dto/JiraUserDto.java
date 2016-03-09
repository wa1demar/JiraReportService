package com.swansoftwaresolutions.jirareport.core.dto;

/**
 * @author Vitaliy Holovko
 */
public class JiraUserDto {
    private Long id;
    private String fullName;
    private String login;
    private Long jiraUserId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getJiraUserId() {
        return jiraUserId;
    }

    public void setJiraUserId(Long jiraUserId) {
        this.jiraUserId = jiraUserId;
    }
}
