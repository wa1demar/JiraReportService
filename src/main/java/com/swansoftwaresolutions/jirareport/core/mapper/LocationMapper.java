package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.locations.LocationDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Location;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface LocationMapper {
    List<LocationDto> fromLocationsToLocationsDto(List<Location> locations);

    Location fromLocationDtoToLocation(LocationDto locationDto);

    LocationDto fromLocationToLocationDto(Location location);
}
