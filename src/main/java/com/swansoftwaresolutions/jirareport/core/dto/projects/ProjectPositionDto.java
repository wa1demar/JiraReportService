package com.swansoftwaresolutions.jirareport.core.dto.projects;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnPriority;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ProjectPositionDto {

    private List<ResourceColumnPriority> items;
    private ProjectFilterData filters;

    public List<ResourceColumnPriority> getItems() {
        return items;
    }

    public void setItems(List<ResourceColumnPriority> items) {
        this.items = items;
    }

    public ProjectFilterData getFilters() {
        return filters;
    }

    public void setFilters(ProjectFilterData filters) {
        this.filters = filters;
    }
}
