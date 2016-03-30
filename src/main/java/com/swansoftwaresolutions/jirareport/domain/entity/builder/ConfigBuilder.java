package com.swansoftwaresolutions.jirareport.domain.entity.builder;

import com.swansoftwaresolutions.jirareport.domain.entity.Config;

/**
 * @author Vladimir Martynyuk
 */
public class ConfigBuilder {
    private Config config;

    public ConfigBuilder() {
        this.config = new Config();
    }

    public ConfigBuilder id(Long id) {
        config.setId(id);
        return this;
    }

    public ConfigBuilder jiraUser(String jiraUser) {
        config.setJiraUser(jiraUser);
        return this;
    }

    public ConfigBuilder jiraPassword(String jiraPassword) {
        config.setJiraPass(jiraPassword);
        return this;
    }

    public ConfigBuilder agileDoneName(String agileDoneName) {
        config.setAgileDoneName(agileDoneName);
        return this;
    }

    public ConfigBuilder jiraDevGroupName(String jiraDevGroupName) {
        config.setJiraDevGroupName(jiraDevGroupName);
        return this;
    }

    public ConfigBuilder bugName(String bugName) {
        config.setBugName(bugName);
        return this;
    }

    public ConfigBuilder nonWorkingDays(String nonWorkingDays) {
        config.setNonWorkingDays(nonWorkingDays);
        return this;
    }

    public Config build() {
        return config;
    }
}
