package com.swansoftwaresolutions.jirareport.core.dto.locations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class LocationsDto {
    private List<LocationDto> locations = new ArrayList<>();

    public List<LocationDto> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDto> locations) {
        this.locations = locations;
    }
}
