package com.swansoftwaresolutions.jirareport.core.dto;

import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class ConfigDto implements Serializable {

    private Long id;
    private String storyPointsName;
    private String agileDoneName;
    private String jiraDevGroupName;
    private String bugName;
    private String nonWorkingDays;
    private String autoSyncTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
