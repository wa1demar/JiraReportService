package com.swansoftwaresolutions.jirareport.core.dto.locations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class LocationsDto {
    private List<LocationDto> items = new ArrayList<>();

    public List<LocationDto> getItems() {
        return items;
    }

    public void setItems(List<LocationDto> items) {
        this.items = items;
    }
}
