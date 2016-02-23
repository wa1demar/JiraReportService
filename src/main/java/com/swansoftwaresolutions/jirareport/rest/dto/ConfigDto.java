package com.swansoftwaresolutions.jirareport.rest.dto;

import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class ConfigDto implements Serializable {

    public Long id;
    public String baseUrl;
    public String jiraUser;
    public String jiraPass;
    public String storyPointsName;
    public String agileDoneName;
    public String jiraDevGroupName;
    public String bugName;
    public String pathToAjax;
    public String pathToAgileRest;
    public String nonWorkingDays;
    public String autoSyncTime;
    public String jiraAuth;
}
