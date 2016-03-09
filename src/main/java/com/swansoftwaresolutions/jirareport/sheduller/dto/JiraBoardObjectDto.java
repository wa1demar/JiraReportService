package com.swansoftwaresolutions.jirareport.sheduller.dto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class JiraBoardObjectDto {
    private int maxResults;
    private boolean isLast;
    private List<JiraBoardDto> values;

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

    public List<JiraBoardDto> getValues() {
        return values;
    }

    public void setValues(List<JiraBoardDto> values) {
        this.values = values;
    }
}
