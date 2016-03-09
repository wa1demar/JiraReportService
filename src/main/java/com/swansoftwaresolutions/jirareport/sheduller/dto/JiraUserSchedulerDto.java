package com.swansoftwaresolutions.jirareport.sheduller.dto;

/**
 * @author Vitaliy Holovko
 */
public class JiraUserSchedulerDto {
//    public int id;
    private String name;
    private String emailAddress;
    private String displayName;
    private boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
