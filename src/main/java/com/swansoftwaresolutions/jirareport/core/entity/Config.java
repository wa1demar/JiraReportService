package com.swansoftwaresolutions.jirareport.core.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */

@Entity
@Table(name="configs")
public class Config implements Serializable {

    private Long id;
    private String baseUrl;
    private String jiraUser;
    private String jiraPass;
    private String storyPointsName;
    private String agileDoneName;
    private String jiraDevGroupName;
    private String bugName;
    private String pathToAjax;
    private String pathToAgileRest;
    private String nonWorkingDays;
    private String autoSyncTime;

    @Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
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

    public String getStoryPointsName() {
        return storyPointsName;
    }

    public void setStoryPointsName(String storyPointsName) {
        this.storyPointsName = storyPointsName;
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

    public String getPathToAjax() {
        return pathToAjax;
    }

    public void setPathToAjax(String pathToAjax) {
        this.pathToAjax = pathToAjax;
    }

    public String getPathToAgileRest() {
        return pathToAgileRest;
    }

    public void setPathToAgileRest(String pathToAgileRest) {
        this.pathToAgileRest = pathToAgileRest;
    }

    public String getNonWorkingDays() {
        return nonWorkingDays;
    }

    public void setNonWorkingDays(String nonWorkingDays) {
        this.nonWorkingDays = nonWorkingDays;
    }

    public String getAutoSyncTime() {
        return autoSyncTime;
    }

    public void setAutoSyncTime(String autoSyncTime) {
        this.autoSyncTime = autoSyncTime;
    }
}
