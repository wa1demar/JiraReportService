package com.swansoftwaresolutions.jirareport.core.dto.sprint;

import java.util.Date;

/**
 * @author Vladimir Martynyuk
 */
public class ImportedSprintDto {
    private int id;
    private String state;
    private String name;
    private Date startDate;
    private Date endDate;
    private Date completeDate;
    private int originBoardId;

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

    public int getOriginBoardId() {
        return originBoardId;
    }

    public void setOriginBoardId(int originBoardId) {
        this.originBoardId = originBoardId;
    }
}
