package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.locations.LocationDto;
import com.swansoftwaresolutions.jirareport.core.dto.locations.LocationsDto;
import com.swansoftwaresolutions.jirareport.core.mapper.LocationMapper;
import com.swansoftwaresolutions.jirareport.core.service.LocationsService;
import com.swansoftwaresolutions.jirareport.domain.entity.Location;
import com.swansoftwaresolutions.jirareport.domain.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class LocationsServiceImpl implements LocationsService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LocationMapper locationMapper;

    @Override
    public LocationsDto findAll() {
        List<Location> locations = locationRepository.findAll();

        LocationsDto locationsDto = new LocationsDto();
        locationsDto.setLocations(locationMapper.fromLocationsToLocationsDto(locations));
        return locationsDto;
    }

    @Override
    public LocationDto add(LocationDto locationDto) {
        Location location = locationRepository.add(locationMapper.fromLocationDtoToLocation(locationDto));

        return locationMapper.fromLocationToLocationDto(location);
    }

    @Override
    public LocationDto delete(Long id) {
        Location location = locationRepository.delete(id);
        return locationMapper.fromLocationToLocationDto(location);
    }

    @Override
    public LocationDto find(Long id) {
        Location location = locationRepository.findById(id);
        return locationMapper.fromLocationToLocationDto(location);
    }

    @Override
    public LocationDto update(LocationDto locationDto) {
        Location location = locationRepository.update(locationMapper.fromLocationDtoToLocation(locationDto));
        return locationMapper.fromLocationToLocationDto(location);
    }
}
