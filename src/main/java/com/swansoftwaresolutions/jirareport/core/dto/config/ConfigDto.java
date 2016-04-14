package com.swansoftwaresolutions.jirareport.core.dto.config;

import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class ConfigDto implements Serializable {

    private Long id;
    private String jiraUser;
    private String jiraPass;
    private String agileDoneName;
    private String jiraDevGroupName;
    private String dueDateIssueStatus;
    private String bugName;
    private String nonWorkingDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgileDoneName() {
        return agileDoneName;
    }

    public void setAgileDoneName(String agileDoneName) {
        this.agileDoneName = agileDoneName;
    }

    public String getJiraDevGroupName() {
        return jiraDevGroupName;
    }

    public void setJiraDevGroupName(String jiraDevGroupName) {
        this.jiraDevGroupName = jiraDevGroupName;
    }

    public String getBugName() {
        return bugName;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName;
    }

    public String getNonWorkingDays() {
        return nonWorkingDays;
    }

    public void setNonWorkingDays(String nonWorkingDays) {
        this.nonWorkingDays = nonWorkingDays;
    }


    public String getJiraUser() {
        return jiraUser;
    }

    public void setJiraUser(String jiraUser) {
        this.jiraUser = jiraUser;
    }

    public String getJiraPass() {
        return jiraPass;
    }

    public void setJiraPass(String jiraPass) {
        this.jiraPass = jiraPass;
    }

    public String getDueDateIssueStatus() {
        return dueDateIssueStatus;
    }

    public void setDueDateIssueStatus(String dueDateIssueStatus) {
        this.dueDateIssueStatus = dueDateIssueStatus;
    }
}
