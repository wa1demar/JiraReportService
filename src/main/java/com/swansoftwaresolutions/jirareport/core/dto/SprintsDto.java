package com.swansoftwaresolutions.jirareport.core.dto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class SprintsDto {
    private int maxResults;
    private boolean isLast;
    private List<SprintDto> values;

    private int getMaxResults() {
        return maxResults;
    }

    private void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    private boolean isLast() {
        return isLast;
    }

    private void setLast(boolean last) {
        isLast = last;
    }

    private List<SprintDto> getValues() {
        return values;
    }

    private void setValues(List<SprintDto> values) {
        this.values = values;
    }
}
