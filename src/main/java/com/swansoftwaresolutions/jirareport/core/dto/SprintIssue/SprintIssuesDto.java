package com.swansoftwaresolutions.jirareport.core.dto.SprintIssue;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class SprintIssuesDto {
    private List<IssuesByDayDto> data;

    public List<IssuesByDayDto> getData() {
        return data;
    }

    public void setData(List<IssuesByDayDto> data) {
        this.data = data;
    }
}
