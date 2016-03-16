package com.swansoftwaresolutions.jirareport.core.dto.sprint;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class NewSprintDto implements Serializable {

    private String name;
    private boolean notCountTarget;
    private boolean showUat;
    private String state;
    private int type;
    private Date endDate;
    private Date startDate;
    private Long reportId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNotCountTarget() {
        return notCountTarget;
    }

    public void setNotCountTarget(boolean notCountTarget) {
        this.notCountTarget = notCountTarget;
    }

    public boolean isShowUat() {
        return showUat;
    }

    public void setShowUat(boolean showUat) {
        this.showUat = showUat;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
}
