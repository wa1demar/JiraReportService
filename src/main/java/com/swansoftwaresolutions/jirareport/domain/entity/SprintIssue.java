package com.swansoftwaresolutions.jirareport.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */

@Entity
@Table(name = "sprint_issues")
public class SprintIssue implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sprint_id")
    private Long sprintId;

    @Column(name = "agile_sprint_id")
    private Long agileSprintId;

    @Column(name = "key")
    private String key;

    @Column(name = "point")
    private double point;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "assignee")
    private String assignee;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "issue_date")
    private String issueDate;

    @Column(name = "hours")
    private Double hours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Long getAgileSprintId() {
        return agileSprintId;
    }

    public void setAgileSprintId(Long agileSprintId) {
        this.agileSprintId = agileSprintId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
}
