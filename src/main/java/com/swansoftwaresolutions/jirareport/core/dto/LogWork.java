package com.swansoftwaresolutions.jirareport.core.dto;

import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class LogWork {
    private String issueKey;
    private String description;
    private Date date;
    private String tyme;

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTyme() {
        return tyme;
    }

    public void setTyme(String tyme) {
        this.tyme = tyme;
    }
}
