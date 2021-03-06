package com.swansoftwaresolutions.jirareport.core.dto.config;

/**
 * @author Vladimir Martynyuk
 */
public class ConfigDtoBuilder {

    private ConfigDto dto;

    public ConfigDtoBuilder() {
        this.dto = new ConfigDto();
    }

    public ConfigDtoBuilder id(Long id) {
        dto.setId(id);
        return this;
    }

    public ConfigDtoBuilder jiraUser(String jiraUser) {
        dto.setJiraUser(jiraUser);
        return this;
    }

    public ConfigDtoBuilder jiraPassword(String jiraPassword) {
        dto.setJiraPass(jiraPassword);
        return this;
    }

    public ConfigDtoBuilder agileDoneName(String agileDoneName) {
        dto.setAgileDoneName(agileDoneName);
        return this;
    }

    public ConfigDtoBuilder dueDateIssueStatus(String dueDateIssueStatus) {
        dto.setDueDateIssueStatus(dueDateIssueStatus);
        return this;
    }

    public ConfigDtoBuilder jiraDevGroupName(String jiraDevGroupName) {
        dto.setJiraDevGroupName(jiraDevGroupName);
        return this;
    }

    public ConfigDtoBuilder bugName(String bugName) {
        dto.setBugName(bugName);
        return this;
    }

    public ConfigDtoBuilder nonWorkingDays(String nonWorkingDays) {
        dto.setNonWorkingDays(nonWorkingDays);
        return this;
    }

    public ConfigDto build() {
        return dto;
    }

}
