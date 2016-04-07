package com.swansoftwaresolutions.jirareport.domain.entity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Entity
@Table(name = "jira_issues")
public class JiraIssue {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    private Date created;

    @Column(name = "updated")
    private Date updated;

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
    private Date dueDate;

    @Column(name = "points")
    private float points;

    @Column(name = "board_id")
    private long boardId;

    @Column(name = "sprint_id")
    private long sprintId;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade={CascadeType.ALL}, mappedBy="issue", fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 10)
    @OrderBy("updatedAt desc")
    private List<DueDate> dueDates = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JiraIssue jiraIssue = (JiraIssue) o;

        if (!key.equals(jiraIssue.key)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    public List<DueDate> getDueDates() {
        return dueDates;
    }

    public void setDueDates(List<DueDate> dates) {
        this.dueDates = dates;
    }
}
