package com.swansoftwaresolutions.jirareport.core.dto.sprint;

import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;

import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class FullSprintDto {
    private long id;
    private String name;
    private boolean notCountTarget;
    private boolean showUat;
    private String state;
    private int type;
    private Date endDate;
    private Date startDate;
    private Long reportId;
    List<SprintDeveloperDto> developers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public List<SprintDeveloperDto> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<SprintDeveloperDto> developers) {
        this.developers = developers;
    }
}