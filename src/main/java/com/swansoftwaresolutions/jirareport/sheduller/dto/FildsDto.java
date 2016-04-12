package com.swansoftwaresolutions.jirareport.sheduller.dto;

import com.swansoftwaresolutions.jirareport.sheduller.dto.fields.*;

import java.util.Date;

/**
 * @author Vitaliy Holovko
 */
public class FildsDto {
    private ProjectDto project;
    private IssueTypeDto issuetype;
    private long timespent;
    private ResolutionDto resolution;
    private Date created;
    private Date updated;
    private AssigneeDto assignee;
    private StatusDto status;
    private AssigneeDto creator;
    private Date duedate;
    private float customfield_10102;
    private String description;
    private String summary;

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }

    public IssueTypeDto getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(IssueTypeDto issuetype) {
        this.issuetype = issuetype;
    }

    public long getTimespent() {
        return timespent;
    }

    public void setTimespent(long timespent) {
        this.timespent = timespent;
    }

    public ResolutionDto getResolution() {
        return resolution;
    }

    public void setResolution(ResolutionDto resolution) {
        this.resolution = resolution;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public AssigneeDto getAssignee() {
        return assignee;
    }

    public void setAssignee(AssigneeDto assignee) {
        this.assignee = assignee;
    }

    public StatusDto getStatus() {
        return status;
    }

    public void setStatus(StatusDto status) {
        this.status = status;
    }

    public AssigneeDto getCreator() {
        return creator;
    }

    public void setCreator(AssigneeDto creator) {
        this.creator = creator;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public float getCustomfield_10102() {
        return customfield_10102;
    }

    public void setCustomfield_10102(float customfield_10102) {
        this.customfield_10102 = customfield_10102;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
