package com.swansoftwaresolutions.jirareport.core.dto;

/**
 * @author Vitaliy Holovko
 */
public class JiraPointDto {
    private int id;
    private String userLogin;
    private long sprintId;
    private int reportId;
    private float points;
    private int issueCount;
    private long issueHourse;
    private int bugUATCount;
    private long bugUATHours;
    private int bugQATCount;
    private long bugQATHourse;
    private long boardId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public long getSprintId() {
        return sprintId;
    }

    public void setSprintId(long sprintId) {
        this.sprintId = sprintId;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public int getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(int issueCount) {
        this.issueCount = issueCount;
    }

    public long getIssueHourse() {
        return issueHourse;
    }

    public void setIssueHourse(long issueHourse) {
        this.issueHourse = issueHourse;
    }

    public int getBugUATCount() {
        return bugUATCount;
    }

    public void setBugUATCount(int bugUATCount) {
        this.bugUATCount = bugUATCount;
    }

    public long getBugUATHours() {
        return bugUATHours;
    }

    public void setBugUATHours(long bugUATHours) {
        this.bugUATHours = bugUATHours;
    }

    public int getBugQATCount() {
        return bugQATCount;
    }

    public void setBugQATCount(int bugQATCount) {
        this.bugQATCount = bugQATCount;
    }

    public long getBugQATHourse() {
        return bugQATHourse;
    }

    public void setBugQATHourse(long bugQATHourse) {
        this.bugQATHourse = bugQATHourse;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }
}
