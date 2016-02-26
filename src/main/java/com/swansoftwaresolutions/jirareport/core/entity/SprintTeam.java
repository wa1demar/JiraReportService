package com.swansoftwaresolutions.jirareport.core.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
@Entity
@Table(name = "sprint_teams")
@NamedQueries(value = {
        @NamedQuery(name = "SprintTeam.findByReportId", query = "FROM SprintTeam c WHERE c.reportId = :reportId"),
        @NamedQuery(name = "SprintTeam.findByReportIdAndAgileSprintId", query = "FROM SprintTeam c WHERE c.reportId = :reportId AND c.agileSprintId = :agileSprintId"),
        @NamedQuery(name = "SprintTeam.findByAgileSprintId", query = "FROM SprintTeam c WHERE c.agileSprintId = :agileSprintId"),
        @NamedQuery(name = "SprintTeam.findById", query = "FROM SprintTeam c WHERE c.id = :id"),
        @NamedQuery(name = "SprintTeam.deleteAll", query = "DELETE FROM SprintTeam c"),
        @NamedQuery(name = "SprintTeam.deleteByReportId", query = "DELETE FROM SprintTeam c WHERE c.reportId = :reportId"),
})
public class SprintTeam implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "agile_sprint_id")
    private Long agileSprintId;

    @Column(name = "dev_name")
    private String devName;

    @Column(name = "engineer_level")
    private Long engineerLevel;

    @Column(name = "participation_level")
    private Double participationLevel;

    @Column(name = "days_in_sprint")
    private Long daysInSprint;

    @Column(name = "target_points")
    private Long targetPoints;

    @Column(name = "target_hours")
    private Long targetHours;

    @Column(name = "defect_min")
    private Long defectMin;

    @Column(name = "defect_max")
    private Long defectMax;

    @Column(name = "defect_hours")
    private Long defectHours;

    @Column(name = "uat_defect_min")
    private Long uatDefectMin;

    @Column(name = "uat_defect_max")
    private Long uatDefectMax;

    @Column(name = "uat_defect_hours")
    private Long uatDefectHours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getAgileSprintId() {
        return agileSprintId;
    }

    public void setAgileSprintId(Long agileSprintId) {
        this.agileSprintId = agileSprintId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
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

    public Long getDaysInSprint() {
        return daysInSprint;
    }

    public void setDaysInSprint(Long daysInSprint) {
        this.daysInSprint = daysInSprint;
    }

    public Long getTargetPoints() {
        return targetPoints;
    }

    public void setTargetPoints(Long targetPoints) {
        this.targetPoints = targetPoints;
    }

    public Long getTargetHours() {
        return targetHours;
    }

    public void setTargetHours(Long targetHours) {
        this.targetHours = targetHours;
    }

    public Long getDefectMin() {
        return defectMin;
    }

    public void setDefectMin(Long defectMin) {
        this.defectMin = defectMin;
    }

    public Long getDefectMax() {
        return defectMax;
    }

    public void setDefectMax(Long defectMax) {
        this.defectMax = defectMax;
    }

    public Long getDefectHours() {
        return defectHours;
    }

    public void setDefectHours(Long defectHours) {
        this.defectHours = defectHours;
    }

    public Long getUatDefectMin() {
        return uatDefectMin;
    }

    public void setUatDefectMin(Long uatDefectMin) {
        this.uatDefectMin = uatDefectMin;
    }

    public Long getUatDefectMax() {
        return uatDefectMax;
    }

    public void setUatDefectMax(Long uatDefectMax) {
        this.uatDefectMax = uatDefectMax;
    }

    public Long getUatDefectHours() {
        return uatDefectHours;
    }

    public void setUatDefectHours(Long uatDefectHours) {
        this.uatDefectHours = uatDefectHours;
    }
}
