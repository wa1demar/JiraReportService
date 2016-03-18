package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.FullSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.NewSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDtos;
import com.swansoftwaresolutions.jirareport.core.service.SprintService;
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
@RequestMapping("/rest/v1/reports")
public class SprintController {

    @Autowired
    SprintService sprintService;

    @RequestMapping(value = "/{report_id}/sprints", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<SprintDto> addNewSprint(@Valid @RequestBody NewSprintDto sprintDto, @PathVariable("report_id") long reportId) {
        sprintDto.setReportId(reportId);

        SprintDto dto = sprintService.add(sprintDto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{report_id}/sprints_with_team", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<FullSprintDto> addNewSprintWithDevelopers(@Valid @RequestBody FullSprintDto sprintDto, @PathVariable("report_id") long reportId) {
        sprintDto.setReportId(reportId);

        FullSprintDto dto = sprintService.add(sprintDto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{report_id}/sprints/{sprint_id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<SprintDto> updateSprint(@Valid @RequestBody SprintDto sprintDto, @PathVariable("report_id") long reportId, @PathVariable("sprint_id") long sprintId) {
        sprintDto.setReportId(reportId);
        sprintDto.setId(sprintId);
        SprintDto dto = sprintService.update(sprintDto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @RequestMapping(value = "/{report_id}/sprints_with_team/{sprint_id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<FullSprintDto> updateSprintWithTeam(@Valid @RequestBody FullSprintDto sprintDto, @PathVariable("report_id") long reportId, @PathVariable("sprint_id") long sprintId) throws NoSuchEntityException {
        sprintDto.setReportId(reportId);
        sprintDto.setId(sprintId);
        FullSprintDto dto = sprintService.update(sprintDto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{report_id}/sprints", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SprintDtos> getByReportId(@PathVariable("report_id") long reportId) {
        SprintDtos dtos = sprintService.findByReportId(reportId);

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{report_id}/sprints/{sprint_id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<SprintDto> deleteSprint(@PathVariable("report_id") long reportId, @PathVariable("sprint_id") long sprintId) throws NoSuchEntityException {
        SprintDto dtos = sprintService.delete(sprintId);

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @RequestMapping(value = "/{report_id}/sprints/{sprint_id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SprintDto> getSprint(@PathVariable("report_id") long reportId, @PathVariable("sprint_id") long sprintId) throws NoSuchEntityException {
        SprintDto dtos = sprintService.findById(sprintId);

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
