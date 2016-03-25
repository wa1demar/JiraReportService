package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;

/**
 * @author Vitaliy Holovko
 */
@Entity
@Table(name = "jira_points")
public class JiraPoint {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_login")
    private String userLogin;

    @Column(name = "sprint_id")
    private long sprintId;

    @Column(name = "report_id")
    private int reportId;

    @Column(name = "points")
    private float points;

    @Column(name = "issue_count")
    private int issueCount;

    @Column(name = "issue_hourse")
    private long issueHourse;

    @Column(name = "uat_count")
    private int bugUATCount;

    @Column(name = "uat_hours")
    private long bugUATHours;

    @Column(name = "qat_count")
    private int bugQATCount;

    @Column(name = "qat_hours")
    private long bugQATHourse;

    @Column(name = "board_id")
    private long boardId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
