package com.swansoftwaresolutions.jirareport.core.dto;

import com.swansoftwaresolutions.jirareport.domain.entity.SprintIssue;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class SprintTeamActivityDataDto implements Serializable {
    private Long sprintId;
    private Date date;
    private List<SprintIssue> issues;

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<SprintIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<SprintIssue> issues) {
        this.issues = issues;
    }
}
