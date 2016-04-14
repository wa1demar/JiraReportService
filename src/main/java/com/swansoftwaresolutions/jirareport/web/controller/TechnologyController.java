package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologiesDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyDto;
import com.swansoftwaresolutions.jirareport.core.service.TechnologiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Vladimir Martynyuk
 */
@RestController
public class TechnologyController {

    @Autowired
    TechnologiesService technologiesService;

    @RequestMapping(value = "/rest/v1/technologies", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<TechnologiesDto> getAllTechnologies() {

        TechnologiesDto technologiesDto = technologiesService.findAll();

        return new ResponseEntity<>(technologiesDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/technologies", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TechnologyDto> addTechnology(@Valid @RequestBody TechnologyDto technologyDto) {

        TechnologyDto newTechnologyDto = technologiesService.add(technologyDto);

        return new ResponseEntity<>(newTechnologyDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/technologies/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<TechnologyDto> deleteLocation(@PathVariable("id") Long id) {

        TechnologyDto deletedTechnologyDto = technologiesService.delete(id);

        return new ResponseEntity<>(deletedTechnologyDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/technologies/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<TechnologyDto> getLocation(@PathVariable("id") Long id) {

        TechnologyDto technologyDto  = technologiesService.find(id);

        return new ResponseEntity<>(technologyDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/technologies/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TechnologyDto> updateLocation(@PathVariable("id") Long id, @Valid @RequestBody TechnologyDto technologyDto) {
        technologyDto.setId(id);
        TechnologyDto updatedTechnologyDto = technologiesService.update(technologyDto);

        return new ResponseEntity<>(updatedTechnologyDto, HttpStatus.OK);
    }
}
