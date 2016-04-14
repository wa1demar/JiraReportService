package com.swansoftwaresolutions.jirareport.sheduller.dto;

import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.ImportedJiraSprintDto;

/**
 * @author Vitaliy Holovko
 */
public class ImportedSprintsDto {
    private int maxResults;
    private int startAt;
    private boolean isLast;
    private ImportedJiraSprintDto[] values;

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public ImportedJiraSprintDto[] getValues() {
        return values;
    }

    public void setValues(ImportedJiraSprintDto[] values) {
        this.values = values;
    }
}
