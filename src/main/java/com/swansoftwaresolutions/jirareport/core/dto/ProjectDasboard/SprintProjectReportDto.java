package com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard;

import java.util.Date;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class SprintProjectReportDto {
    private Long id;
    private Long reportId;
    private Long agileSprintId;
    private boolean notCountTarget;
    private String name;
    private String state;
    private int type;
    private Date startDate;
    private Date endDate;
    private Date completeDate;
    private boolean showUat;
    private boolean closed;
    private float targetPoints;
    private Long targetHours;
    private int targetQatDefectMin;
    private int targetQatDefectMax;
    private Long targetQatDefectHours;
    private int targetUatDefectMin;
    private int targetUatDefectMax;
    private Long targetUatDefectHours;
    private float actualPoints;
    private Long actualHours;
    private float actualQatDefectPoints;
    private Long actualQatDefectHours;
    private float actualUatDefectPoints;
    private Long actualUatDefectHours;

    private Chart chart;

    private List<SprintTeamDto> sprintTeam;

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

    public boolean isNotCountTarget() {
        return notCountTarget;
    }

    public void setNotCountTarget(boolean notCountTarget) {
        this.notCountTarget = notCountTarget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public boolean isShowUat() {
        return showUat;
    }

    public void setShowUat(boolean showUat) {
        this.showUat = showUat;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public float getTargetPoints() {
        return targetPoints;
    }

    public void setTargetPoints(float targetPoints) {
        this.targetPoints = targetPoints;
    }

    public Long getTargetHours() {
        return targetHours;
    }

    public void setTargetHours(Long targetHours) {
        this.targetHours = targetHours;
    }

    public int getTargetQatDefectMin() {
        return targetQatDefectMin;
    }

    public void setTargetQatDefectMin(int targetQatDefectMin) {
        this.targetQatDefectMin = targetQatDefectMin;
    }

    public int getTargetQatDefectMax() {
        return targetQatDefectMax;
    }

    public void setTargetQatDefectMax(int targetQatDefectMax) {
        this.targetQatDefectMax = targetQatDefectMax;
    }

    public Long getTargetQatDefectHours() {
        return targetQatDefectHours;
    }

    public void setTargetQatDefectHours(Long targetQatDefectHours) {
        this.targetQatDefectHours = targetQatDefectHours;
    }

    public int getTargetUatDefectMin() {
        return targetUatDefectMin;
    }

    public void setTargetUatDefectMin(int targetUatDefectMin) {
        this.targetUatDefectMin = targetUatDefectMin;
    }

    public int getTargetUatDefectMax() {
        return targetUatDefectMax;
    }

    public void setTargetUatDefectMax(int targetUatDefectMax) {
        this.targetUatDefectMax = targetUatDefectMax;
    }

    public Long getTargetUatDefectHours() {
        return targetUatDefectHours;
    }

    public void setTargetUatDefectHours(Long targetUatDefectHours) {
        this.targetUatDefectHours = targetUatDefectHours;
    }

    public float getActualPoints() {
        return actualPoints;
    }

    public void setActualPoints(float actualPoints) {
        this.actualPoints = actualPoints;
    }

    public Long getActualHours() {
        return actualHours;
    }

    public void setActualHours(Long actualHours) {
        this.actualHours = actualHours;
    }

    public float getActualQatDefectPoints() {
        return actualQatDefectPoints;
    }

    public void setActualQatDefectPoints(float actualQatDefectPoints) {
        this.actualQatDefectPoints = actualQatDefectPoints;
    }

    public Long getActualQatDefectHours() {
        return actualQatDefectHours;
    }

    public void setActualQatDefectHours(Long actualQatDefectHours) {
        this.actualQatDefectHours = actualQatDefectHours;
    }

    public float getActualUatDefectPoints() {
        return actualUatDefectPoints;
    }

    public void setActualUatDefectPoints(float actualUatDefectPoints) {
        this.actualUatDefectPoints = actualUatDefectPoints;
    }

    public Long getActualUatDefectHours() {
        return actualUatDefectHours;
    }

    public void setActualUatDefectHours(Long actualUatDefectHours) {
        this.actualUatDefectHours = actualUatDefectHours;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public List<SprintTeamDto> getSprintTeam() {
        return sprintTeam;
    }

    public void setSprintTeam(List<SprintTeamDto> sprintTeam) {
        this.sprintTeam = sprintTeam;
    }
}
