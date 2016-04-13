package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.locations.LocationDto;
import com.swansoftwaresolutions.jirareport.core.mapper.LocationMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Location;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class LocationMapperImpl implements LocationMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<LocationDto> fromLocationsToLocationsDto(List<Location> locations) {
        Type targetistType = new TypeToken<List<LocationDto>>() {}.getType();
        return modelMapper.map(locations, targetistType);
    }

    @Override
    public Location fromLocationDtoToLocation(LocationDto locationDto) {
        return modelMapper.map(locationDto, Location.class);
    }

    @Override
    public LocationDto fromLocationToLocationDto(Location location) {
        return modelMapper.map(location, LocationDto.class);
    }
}
