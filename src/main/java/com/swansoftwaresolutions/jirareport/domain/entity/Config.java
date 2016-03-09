package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */

@Entity
@Table(name = "configs")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "story_point_name")
    private String storyPointsName;

    @Column(name = "agile_done_name")
    private String agileDoneName;

    @Column(name = "jira_dev_group_name")
    private String jiraDevGroupName;

    @Column(name = "bug_name")
    private String bugName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Config config = (Config) o;

        if (!storyPointsName.equals(config.storyPointsName)) return false;
        if (agileDoneName != null ? !agileDoneName.equals(config.agileDoneName) : config.agileDoneName != null)
            return false;
        if (jiraDevGroupName != null ? !jiraDevGroupName.equals(config.jiraDevGroupName) : config.jiraDevGroupName != null)
            return false;
        return bugName.equals(config.bugName);

    }

    @Override
    public int hashCode() {
        int result = storyPointsName.hashCode();
        result = 31 * result + (agileDoneName != null ? agileDoneName.hashCode() : 0);
        result = 31 * result + (jiraDevGroupName != null ? jiraDevGroupName.hashCode() : 0);
        result = 31 * result + bugName.hashCode();
        return result;
    }
}
