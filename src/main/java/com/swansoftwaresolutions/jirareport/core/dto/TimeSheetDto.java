package com.swansoftwaresolutions.jirareport.core.dto;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class TimeSheetDto {

    private String userName;
    private List<LogWork> issues;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<LogWork> getIssues() {
        return issues;
    }

    public void setIssues(List<LogWork> issues) {
        this.issues = issues;
    }
}
