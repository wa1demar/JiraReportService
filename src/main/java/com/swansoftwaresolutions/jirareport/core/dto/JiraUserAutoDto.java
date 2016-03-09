package com.swansoftwaresolutions.jirareport.core.dto;

/**
 * @author Vitaliy Holovko
 */
public class JiraUserAutoDto {
    private Long id;
    private String email;
    private String login;
    private String fullName;
    private Long jiraUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JiraUserAutoDto that = (JiraUserAutoDto) o;

        if (!fullName.equals(that.fullName)) return false;
        if (!login.equals(that.login)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }
}
