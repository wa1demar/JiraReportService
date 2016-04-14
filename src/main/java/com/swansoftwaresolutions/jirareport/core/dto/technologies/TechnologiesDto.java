package com.swansoftwaresolutions.jirareport.core.dto.technologies;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class TechnologiesDto {

    private List<TechnologyDto> items = new ArrayList<>();

    public List<TechnologyDto> getItems() {
        return items;
    }

    public void setItems(List<TechnologyDto> items) {
        this.items = items;
    }
}
