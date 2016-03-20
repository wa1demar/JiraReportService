package com.swansoftwaresolutions.jirareport.core.dto.SprintIssue;

import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class IssuesByDayDto {
    private String date;
    private List<SprintIssueDto> issues = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<SprintIssueDto> getIssues() {
        return issues;
    }

    public void setIssues(List<SprintIssueDto> issues) {
        this.issues = issues;
    }
}
