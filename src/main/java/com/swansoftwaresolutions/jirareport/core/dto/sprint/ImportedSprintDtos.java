package com.swansoftwaresolutions.jirareport.core.dto.sprint;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ImportedSprintDtos implements Serializable {
    private int maxResults;
    private boolean isLast;
    private List<ImportedSprintDto> values;

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

    public List<ImportedSprintDto> getValues() {
        return values;
    }

    public void setValues(List<ImportedSprintDto> values) {
        this.values = values;
    }
}
