package com.swansoftwaresolutions.jirareport.core.dto.resourceboard;

/**
 * @author Vladimir Martynyuk
 */
public class ResourceColumnPriority {
    private Long columnId;
    private int columnPriority;

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public int getColumnPriority() {
        return columnPriority;
    }

    public void setColumnPriority(int columnPriority) {
        this.columnPriority = columnPriority;
    }
}
