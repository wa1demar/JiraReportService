package com.swansoftwaresolutions.jirareport.core.dto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class ImportedBardsDto {
    private int maxResults;
    private boolean isLast;
    private List<ImportedJiraBoardDto> values;

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

    public List<ImportedJiraBoardDto> getValues() {
        return values;
    }

    public void setValues(List<ImportedJiraBoardDto> values) {
        this.values = values;
    }
}
