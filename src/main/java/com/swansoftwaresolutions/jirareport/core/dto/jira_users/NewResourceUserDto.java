package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

/**
 * @author Vladimir Martynyuk
 */
public class NewResourceUserDto {

    private String userLogin;
    private Integer level;
    private Long location;
    private Long[] technologies;
    private String description;

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long[] getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Long[] technologies) {
        this.technologies = technologies;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }
}
