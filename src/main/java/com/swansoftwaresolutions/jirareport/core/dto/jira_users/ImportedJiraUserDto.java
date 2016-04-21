package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vladimir Martynyuk
 */
public class ImportedJiraUserDto {
    private String name;
    private String key;
    private String emailAddress;
    private String displayName;
    private LinkedHashMap<String, String> avatarUrls = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LinkedHashMap<String, String> getAvatarUrls() {
        return avatarUrls;
    }

    public void setAvatarUrls(LinkedHashMap<String, String> avatarUrls) {
        this.avatarUrls = avatarUrls;
    }

    public String getAvatarUrl() {
        return avatarUrls.get("48x48");
    }
}
