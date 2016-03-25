package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;

/**
 * @author Vitaliy Holovko
 */

@Entity
@Table(name = "jira_issues")
public class JiraIssue {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "key")
    private String key;

    @Column(name = "project_jira_id")
    private int projectId;

    @Column(name = "project_jira_key")
    private String projectKey;

    @Column(name = "issue_type_id")
    private int issueTypeId;

    @Column(name = "issue_type_name")
    private String issueTypeName;

    @Column(name = "issue_type_subtask")
    private boolean issueTypeSubTask;

    @Column(name = "time_spent")
    private long timeSpent;

    @Column(name = "resolution_id")
    private int resolutionId;

    @Column(name = "resolution_name")
    private String resolutionName;

    @Column(name = "created")
    private String created;

    @Column(name = "updated")
    private String updated;

    @Column(name = "assigned_name")
    private String assignedName;

    @Column(name = "assigned_key")
    private String assignedKey;

    @Column(name = "assigned_full_name")
    private String assignedFullName;

    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "creator_name_key")
    private String creatorKey;

    @Column(name = "creator_full_name")
    private String creatorFullName;

    @Column(name = "status_id")
    private int statusId;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name = "points")
    private float points;

    @Column(name = "board_id")
    private long boardId;

    @Column(name = "sprint_id")
    private long sprintId;

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

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
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

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public String getAssignedName() {
        return assignedName;
    }

    public void setAssignedName(String assignedName) {
        this.assignedName = assignedName;
    }

    public String getAssignedKey() {
        return assignedKey;
    }

    public void setAssignedKey(String assignedKey) {
        this.assignedKey = assignedKey;
    }

    public String getAssignedFullName() {
        return assignedFullName;
    }

    public void setAssignedFullName(String assignedFullName) {
        this.assignedFullName = assignedFullName;
    }

    public long getSprintId() {
        return sprintId;
    }

    public void setSprintId(long sprintId) {
        this.sprintId = sprintId;
    }
}
