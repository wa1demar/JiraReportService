package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintsDto;
import com.swansoftwaresolutions.jirareport.core.service.JiraSprintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest/v1/reports")
public class SprintController {

    @Autowired
    JiraSprintsService jiraSprintsService;

    @RequestMapping(value = "/{report_id}/sprints", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SprintsDto> retrieveByReportId(@PathVariable("report_id") long report_id) {
        SprintsDto sprintsDto = jiraSprintsService.retrieveByReportId(report_id);
        return new ResponseEntity<>(sprintsDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{report_id}/sprints", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<SprintDto> addNewSprint(@Valid @RequestBody SprintDto sprintDto, @PathVariable("report_id") long report_id) {
        sprintDto.setReportId(report_id);
        SprintDto dto = jiraSprintsService.add(sprintDto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
