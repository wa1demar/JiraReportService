package com.swansoftwaresolutions.jirareport.domain.entity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "sprint_developers")
public class SprintDeveloper implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jira_user_login")
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 10)
    private JiraUser jiraUser;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 10)
    @JoinColumn(name="sprint_id")
    private Sprint sprint;

    @Column(name = "engineer_level")
    private Long engineerLevel;

    @Column(name = "participation_level")
    private Double participationLevel;

    @Column(name = "days_in_sprint")
    private int daysInSprint;

    @Column(name = "defect_hours")
    private Long defectHours;

    @Column(name = "defect_max")
    private int defectMax;

    @Column(name = "defect_min")
    private int defectMin;

    @Column(name = "target_hours")
    private Long targetHours;

    @Column(name = "target_points")
    private float targetPoints;

    @Column(name = "uat_defect_hours")
    private Long uatDefectHours;

    @Column(name = "uat_defect_max")
    private int uatDefectMax;

    @Column(name = "uat_defect_min")
    private int uatDefectMin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JiraUser getJiraUser() {
        return jiraUser;
    }

    public void setJiraUser(JiraUser jiraUser) {
        this.jiraUser = jiraUser;
    }

    public Long getEngineerLevel() {
        return engineerLevel;
    }

    public void setEngineerLevel(Long engineerLevel) {
        this.engineerLevel = engineerLevel;
    }

    public Double getParticipationLevel() {
        return participationLevel;
    }

    public void setParticipationLevel(Double participationLevel) {
        this.participationLevel = participationLevel;
    }

    public int getDaysInSprint() {
        return daysInSprint;
    }

    public void setDaysInSprint(int daysInSprint) {
        this.daysInSprint = daysInSprint;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Long getDefectHours() {
        return defectHours;
    }

    public void setDefectHours(Long defectHours) {
        this.defectHours = defectHours;
    }

    public int getDefectMax() {
        return defectMax;
    }

    public void setDefectMax(int defectMax) {
        this.defectMax = defectMax;
    }

    public int getDefectMin() {
        return defectMin;
    }

    public void setDefectMin(int defectMin) {
        this.defectMin = defectMin;
    }

    public Long getTargetHours() {
        return targetHours;
    }

    public void setTargetHours(Long targetHours) {
        this.targetHours = targetHours;
    }

    public float getTargetPoints() {
        return targetPoints;
    }

    public void setTargetPoints(float targetPoints) {
        this.targetPoints = targetPoints;
    }

    public Long getUatDefectHours() {
        return uatDefectHours;
    }

    public void setUatDefectHours(Long uatDefectHours) {
        this.uatDefectHours = uatDefectHours;
    }

    public int getUatDefectMax() {
        return uatDefectMax;
    }

    public void setUatDefectMax(int uatDefectMax) {
        this.uatDefectMax = uatDefectMax;
    }

    public int getUatDefectMin() {
        return uatDefectMin;
    }

    public void setUatDefectMin(int uatDefectMin) {
        this.uatDefectMin = uatDefectMin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SprintDeveloper developer = (SprintDeveloper) o;

        if (daysInSprint != developer.daysInSprint) return false;
        if (!id.equals(developer.id)) return false;
        if (!jiraUser.equals(developer.jiraUser)) return false;
        if (sprint != null ? !sprint.equals(developer.sprint) : developer.sprint != null) return false;
        if (!engineerLevel.equals(developer.engineerLevel)) return false;
        return participationLevel.equals(developer.participationLevel);

    }

    @Override
    public int hashCode() {
        int result = jiraUser.hashCode();
        result = 31 * result + (sprint != null ? sprint.hashCode() : 0);
        result = 31 * result + engineerLevel.hashCode();
        result = 31 * result + participationLevel.hashCode();
        result = 31 * result + daysInSprint;
        return result;
    }
}

