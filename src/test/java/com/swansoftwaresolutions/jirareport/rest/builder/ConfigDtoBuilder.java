package com.swansoftwaresolutions.jirareport.rest.builder;

import com.swansoftwaresolutions.jirareport.rest.dto.ConfigDto;

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

    public ConfigDtoBuilder storyPointsName(String storyPointsName) {
        dto.setStoryPointsName(storyPointsName);
        return this;
    }

    public ConfigDtoBuilder agileDoneName(String agileDoneName) {
        dto.setAgileDoneName(agileDoneName);
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

    public ConfigDtoBuilder autoSyncTime(String autoSyncTime) {
        dto.setAutoSyncTime(autoSyncTime);
        return this;
    }

    public ConfigDto build() {
        return dto;
    }

}
