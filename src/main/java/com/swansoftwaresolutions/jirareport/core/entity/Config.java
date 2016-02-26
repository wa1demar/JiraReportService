package com.swansoftwaresolutions.jirareport.core.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */

@Entity
@Table(name = "configs")
@NamedQueries(value = {
        @NamedQuery(name = "Config.findById", query = "FROM Config c WHERE c.id = :id"),
        @NamedQuery(name = "Config.deleteAll", query = "DELETE FROM Config c"),
        @NamedQuery(name = "Config.deleteById", query = "DELETE FROM Config c WHERE c.id = :id"),
})
public class Config implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "base_url")
    private String baseUrl;

    @Column(name = "jira_user")
    private String jiraUser;

    @Column(name = "jira_password")
    private String jiraPassword;

    @Column(name = "story_point_name")
    private String storyPointsName;

    @Column(name = "agile_done_name")
    private String agileDoneName;

    @Column(name = "jira_dev_group_name")
    private String jiraDevGroupName;

    @Column(name = "bug_name")
    private String bugName;

    @Column(name = "path_to_ajax")
    private String pathToAjax;

    @Column(name = "path_to_agile_rest")
    private String pathToAgileRest;

    @Column(name = "non_working_days")
    private String nonWorkingDays;

    @Column(name = "auto_sync_time")
    private String autoSyncTime;

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

    public String getJiraPassword() {
        return jiraPassword;
    }

    public void setJiraPassword(String jiraPassword) {
        this.jiraPassword = jiraPassword;
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
