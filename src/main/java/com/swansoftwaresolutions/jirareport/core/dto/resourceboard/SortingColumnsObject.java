package com.swansoftwaresolutions.jirareport.core.dto.resourceboard;

/**
 * @author Vladimir Martynyuk
 */
public class SortingColumnsObject {
    private ResourceColumnPriority[] items;
    private ResourceFilterData filters;

    public ResourceFilterData getFilters() {
        return filters;
    }

    public void setFilters(ResourceFilterData filters) {
        this.filters = filters;
    }

    public ResourceColumnPriority[] getItems() {
        return items;
    }

    public void setItems(ResourceColumnPriority[] items) {
        this.items = items;
    }
}
