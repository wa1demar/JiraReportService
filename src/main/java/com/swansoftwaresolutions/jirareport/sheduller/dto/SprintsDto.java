package com.swansoftwaresolutions.jirareport.sheduller.dto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class SprintsDto {
    private int maxResults;
    private boolean isLast;
    private List<SprintDto> values;

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

    public List<SprintDto> getValues() {
        return values;
    }

    public void setValues(List<SprintDto> values) {
        this.values = values;
    }
}
