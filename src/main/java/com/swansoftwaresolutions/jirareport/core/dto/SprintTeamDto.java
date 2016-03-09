package com.swansoftwaresolutions.jirareport.core.dto;

import java.io.Serializable;

/**
 * @author Vladimir Martynyuk
 */
public class SprintTeamDto implements Serializable {
    private Long id;
    private Long reportId;
    private Long agileSprintId;
    private String devName;
    private Long engineerLevel;
    private Double participationLevel;
    private Long daysInSprint;

    private Long targetPoints;
    private Long targetHours;

    private Long defectMin;
    private Long defectMax;
    private Long defectHours;

    private Long uatDefectMin;
    private Long uatDefectMax;
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
