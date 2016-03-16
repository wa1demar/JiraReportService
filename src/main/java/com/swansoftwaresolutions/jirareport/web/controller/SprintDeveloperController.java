package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.NewSprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDevelopersDto;
import com.swansoftwaresolutions.jirareport.core.service.SprintDeveloperService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest/v1/sprints")
public class SprintDeveloperController {

    @Autowired
    SprintDeveloperService developerService;

    @RequestMapping(value = "/{sprint_id}/developers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SprintDevelopersDto> getDevelopersBySprint(@PathVariable("sprint_id") long sprintId) {

        SprintDevelopersDto dtos = developerService.findBySprint(sprintId);

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sprint_id}/developers", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<SprintDeveloperDto> addDeveloperToSprint(@Valid @RequestBody NewSprintDeveloperDto dto, @PathVariable("sprint_id") long sprintId) throws NoSuchEntityException {
        SprintDeveloperDto newDeveloper = developerService.add(dto, sprintId);
        return new ResponseEntity<>(newDeveloper, HttpStatus.OK);
    }
}
