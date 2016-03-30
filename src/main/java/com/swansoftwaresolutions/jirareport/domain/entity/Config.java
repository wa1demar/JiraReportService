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

    @Column(name = "agile_done_name")
    private String agileDoneName;

    @Column(name = "jira_dev_group_name")
    private String jiraDevGroupName;

    @Column(name = "bug_name")
    private String bugName;

    @Column(name = "non_working_days")
    private String nonWorkingDays;

    @Column(name = "jira_user")
    private String jiraUser;

    @Column(name = "jira_password")
    private String jiraPass;


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

    public void setJiraPass(String jiraPassword) {
        this.jiraPass = jiraPassword;
    }
}
