package com.swansoftwaresolutions.jirareport.core.dto.resourceboard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ResourceColumnDtos {
    private List<ResourceColumnDto> columns = new ArrayList<>();

    public List<ResourceColumnDto> getColumns() {
        return columns;
    }

    public void setColumns(List<ResourceColumnDto> columns) {
        this.columns = columns;
    }
}
