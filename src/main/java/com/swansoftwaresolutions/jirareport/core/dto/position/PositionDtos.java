package com.swansoftwaresolutions.jirareport.core.dto.position;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class PositionDtos {
    private List<PositionDto> items = new ArrayList<>();

    public List<PositionDto> getItems() {
        return items;
    }

    public void setItems(List<PositionDto> items) {
        this.items = items;
    }
}
