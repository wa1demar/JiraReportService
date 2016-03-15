package com.swansoftwaresolutions.jirareport.core.dto.jira_sprint;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ImportedJiraSprintDtos implements Serializable {
    private int maxResults;
    private boolean isLast;
    private List<ImportedJiraSprintDto> values;

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public List<ImportedJiraSprintDto> getValues() {
        return values;
    }

    public void setValues(List<ImportedJiraSprintDto> values) {
        this.values = values;
    }
}
