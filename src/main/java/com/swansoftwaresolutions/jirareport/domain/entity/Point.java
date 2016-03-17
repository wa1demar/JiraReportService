package com.swansoftwaresolutions.jirareport.domain.entity;

/**
 * @author Vitaliy Holovko
 */
public class Point {
    private int id;
    private String userLogin;
    private Long sprintId;
    private int reportId;
    private float points;
    private int issueCount;
    private Long issueHourse;
    private int bugUATCount;
    private Long bugUATHours;
    private int bugQATCount;
    private Long bugQATHourse;

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

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
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

    public Long getIssueHourse() {
        return issueHourse;
    }

    public void setIssueHourse(Long issueHourse) {
        this.issueHourse = issueHourse;
    }

    public int getBugUATCount() {
        return bugUATCount;
    }

    public void setBugUATCount(int bugUATCount) {
        this.bugUATCount = bugUATCount;
    }

    public Long getBugUATHours() {
        return bugUATHours;
    }

    public void setBugUATHours(Long bugUATHours) {
        this.bugUATHours = bugUATHours;
    }

    public int getBugQATCount() {
        return bugQATCount;
    }

    public void setBugQATCount(int bugQATCount) {
        this.bugQATCount = bugQATCount;
    }

    public Long getBugQATHourse() {
        return bugQATHourse;
    }

    public void setBugQATHourse(Long bugQATHourse) {
        this.bugQATHourse = bugQATHourse;
    }
}
