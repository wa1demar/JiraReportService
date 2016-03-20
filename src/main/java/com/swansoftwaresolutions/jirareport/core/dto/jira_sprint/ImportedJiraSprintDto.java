package com.swansoftwaresolutions.jirareport.core.dto.jira_sprint;

import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class ImportedJiraSprintDto {
    private int id;
    private String state;
    private String name;
    private Date startDate;
    private Date endDate;
    private Date completeDate;
    private Long originBoardId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getOriginBoardId() {
        return originBoardId;
    }

    public void setOriginBoardId(Long originBoardId) {
        this.originBoardId = originBoardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImportedJiraSprintDto that = (ImportedJiraSprintDto) o;

        if (!name.equals(that.name)) return false;
        if (!originBoardId.equals(that.originBoardId)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + originBoardId.hashCode();
        return result;
    }
}
