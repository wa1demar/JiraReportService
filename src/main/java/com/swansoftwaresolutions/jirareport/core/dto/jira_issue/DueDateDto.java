package com.swansoftwaresolutions.jirareport.core.dto.jira_issue;

import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class DueDateDto {
    private Long id;
    private String project;
    private String assignee;
    private String key;
    private String summary;
    private String[] description;
    private String status;
    private Date[] dueDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String[] getDescription() {
        return description;
    }

    public void setDescription(String[] description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date[] getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date[] dueDate) {
        this.dueDate = dueDate;
    }
}
