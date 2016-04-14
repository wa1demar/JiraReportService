package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.locations.LocationDto;
import com.swansoftwaresolutions.jirareport.core.dto.locations.LocationsDto;

/**
 * @author Vladimir Martynyuk
 */
public interface LocationsService {
    LocationsDto findAll();

    LocationDto add(LocationDto locationDto);

    LocationDto delete(Long id);

    LocationDto find(Long id);

    LocationDto update(LocationDto locationDto);
}
