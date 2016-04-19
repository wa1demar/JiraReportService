package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.locations.LocationDto;
import com.swansoftwaresolutions.jirareport.core.dto.locations.LocationsDto;
import com.swansoftwaresolutions.jirareport.core.service.LocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Vladimir Martynyuk
 */
@RestController
public class LocationController {

    @Autowired
    LocationsService locationService;

    @RequestMapping(value = "/rest/v1/locations", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<LocationsDto> getAllLocations() {

        LocationsDto locationsDto = locationService.findAll();

        return new ResponseEntity<>(locationsDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/locations", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<LocationDto> addLocation(@Valid @RequestBody LocationDto locationDto) {

        LocationDto newLocationDto = locationService.add(locationDto);

        return new ResponseEntity<>(newLocationDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/locations/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<LocationDto> deleteLocation(@PathVariable("id") Long id) {

        LocationDto deletedLocationDto = locationService.delete(id);

        return new ResponseEntity<>(deletedLocationDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/locations/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<LocationDto> getLocation(@PathVariable("id") Long id) {

        LocationDto locationDto = locationService.find(id);

        return new ResponseEntity<>(locationDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/locations/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<LocationDto> updateLocation(@PathVariable("id") Long id, @Valid @RequestBody LocationDto locationDto) {
        locationDto.setId(id);
        LocationDto updatedLocationDto = locationService.update(locationDto);

        return new ResponseEntity<>(updatedLocationDto, HttpStatus.OK);
    }
}
