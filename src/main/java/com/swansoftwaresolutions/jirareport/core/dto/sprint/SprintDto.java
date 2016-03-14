package com.swansoftwaresolutions.jirareport.core.dto.sprint;

import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class SprintDto {

    private Long id;
    private Long reportId;
    private Long agileSprintId;
    private boolean notCountTarget;
    private String name;
    private String state;
    private Long type;
    private Date startDate;
    private Date endDate;
    private Date completeDate;
    private boolean showUat;

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

    public boolean getNotCountTarget() {
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

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
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

    public boolean getShowUat() {
        return showUat;
    }

    public void setShowUat(boolean showUat) {
        this.showUat = showUat;
    }
}
