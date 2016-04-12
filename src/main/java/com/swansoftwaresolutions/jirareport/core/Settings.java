package com.swansoftwaresolutions.jirareport.core;

import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;
import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDtoBuilder;

/**
 * @author Vladimir Martynyuk
 */
public class Settings {

    private static Long id;
    private static String jiraUser;
    private static String jiraPassword;
    private static String agileDoneName;
    private static String dueDateIssueStatus;
    private static String devGroupName;
    private static String bugName;
    private static String nonWorkingDays;

    public static void init(Long _id, String _jiraUser, String _jiraPassword, String _agileDoneName, String _devGroupName, String _bugName, String _nonWorkingDays, String _dueDateIssueStatus) {
        id = _id;
        jiraUser = _jiraUser;
        jiraPassword = _jiraPassword;
        agileDoneName = _agileDoneName;
        devGroupName = _devGroupName;
        bugName = _bugName;
        nonWorkingDays = _nonWorkingDays;
        dueDateIssueStatus = _dueDateIssueStatus;
    }

    public static Boolean isNotEmpty() {
        if (jiraUser != null && jiraPassword != null && agileDoneName != null && devGroupName != null && bugName != null && nonWorkingDays != null && dueDateIssueStatus != null) {
            return true;
        }
        return false;
    }

    public static ConfigDto configDto() {
        return new ConfigDtoBuilder()
                .id(id)
                .jiraUser(jiraUser)
                .jiraPassword(jiraPassword)
                .agileDoneName(agileDoneName)
                .bugName(bugName)
                .jiraDevGroupName(devGroupName)
                .nonWorkingDays(nonWorkingDays)
                .dueDateIssueStatus(dueDateIssueStatus)
                .build();
    }
}
