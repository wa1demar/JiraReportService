package com.swansoftwaresolutions.jirareport.core.dto.resourceboard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class FullResourceColumnDtoList {
    private List<FullResourceColumnDto> columns = new ArrayList<FullResourceColumnDto>();

    public List<FullResourceColumnDto> getColumns() {
        return columns;
    }

    public void setColumns(List<FullResourceColumnDto> columns) {
        this.columns = columns;
    }
}
