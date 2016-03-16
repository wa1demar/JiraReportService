package com.swansoftwaresolutions.jirareport.sheduller.dto;

/**
 * @author Vitaliy Holovko
 */
public class JiraIssueDto {
    private int id;
    private String key;
    private int projectId;
    private String projectKey;
    private int issueTypeId;
    private String issueTypeName;
    private boolean issueTypeSubTask;
    private Long timeSpent;
    private int resolutionId;
    private String resolutionName;
    private String created;
    private String updated;
    private String assignetName;
    private String assignetKey;
    private String assignetFullName;
    private String creatorName;
    private String creatorKey;
    private String creatorFullName;
    private int statusId;
    private String statusName;
    private String dueDate;
    private float points;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public int getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(int issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public String getIssueTypeName() {
        return issueTypeName;
    }

    public void setIssueTypeName(String issueTypeName) {
        this.issueTypeName = issueTypeName;
    }

    public boolean isIssueTypeSubTask() {
        return issueTypeSubTask;
    }

    public void setIssueTypeSubTask(boolean issueTypeSubTask) {
        this.issueTypeSubTask = issueTypeSubTask;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getResolutionId() {
        return resolutionId;
    }

    public void setResolutionId(int resolutionId) {
        this.resolutionId = resolutionId;
    }

    public String getResolutionName() {
        return resolutionName;
    }

    public void setResolutionName(String resolutionName) {
        this.resolutionName = resolutionName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getAssignetName() {
        return assignetName;
    }

    public void setAssignetName(String assignetName) {
        this.assignetName = assignetName;
    }

    public String getAssignetKey() {
        return assignetKey;
    }

    public void setAssignetKey(String assignetKey) {
        this.assignetKey = assignetKey;
    }

    public String getAssignetFullName() {
        return assignetFullName;
    }

    public void setAssignetFullName(String assignetFullName) {
        this.assignetFullName = assignetFullName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorKey() {
        return creatorKey;
    }

    public void setCreatorKey(String creatorKey) {
        this.creatorKey = creatorKey;
    }

    public String getCreatorFullName() {
        return creatorFullName;
    }

    public void setCreatorFullName(String creatorFullName) {
        this.creatorFullName = creatorFullName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }
}
