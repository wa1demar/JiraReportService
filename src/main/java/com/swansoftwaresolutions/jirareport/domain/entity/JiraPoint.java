package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;

/**
 * @author Vitaliy Holovko
 */
@Entity
@Table(name = "points")
public class JiraPoint {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_login")
    private String userLogin;

    @Column(name = "sprint_id")
    private Long sprintId;

    @Column(name = "report_id")
    private int reportId;

    @Column(name = "points")
    private float points;

    @Column(name = "issue_count")
    private int issueCount;

    @Column(name = "issue_hourse")
    private Long issueHourse;

    @Column(name = "uat_count")
    private int bugUATCount;

    @Column(name = "uat_hours")
    private Long bugUATHours;

    @Column(name = "qat_count")
    private int bugQATCount;

    @Column(name = "qat_hours")
    private Long bugQATHourse;

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
