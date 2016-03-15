package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.JiraSprintsDto;
import com.swansoftwaresolutions.jirareport.core.service.JiraSprintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest/v1/boards")
public class JiraSprintController {

    @Autowired
    JiraSprintsService jiraSprintsService;

    @RequestMapping(value = "/{board_id}/sprints", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JiraSprintsDto> retrieveAllSprintsByBoardId(@PathVariable("board_id") long board_id) {
        JiraSprintsDto jiraSprintsDto = jiraSprintsService.retrieveByBoardId(board_id);

        return new ResponseEntity<>(jiraSprintsDto, HttpStatus.OK);
    }


//    @RequestMapping(value = "/{report_id}/sprints", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<JiraSprintsDto> retrieveByReportId(@PathVariable("report_id") long report_id) {
//        JiraSprintsDto jiraSprintsDto = jiraSprintsService.retrieveByReportId(report_id);
//        return new ResponseEntity<>(jiraSprintsDto, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{report_id}/sprints", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<JiraSprintDto> addNewSprint(@Valid @RequestBody JiraSprintDto jiraSprintDto, @PathVariable("report_id") long report_id) {
//        jiraSprintDto.setReportId(report_id);
//        JiraSprintDto dto = jiraSprintsService.add(jiraSprintDto);
//
//        return new ResponseEntity<>(dto, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{report_id}/sprints/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public ResponseEntity<JiraSprintDto> addNewSprint(@PathVariable("report_id") long report_id, @PathVariable("id") long id) throws NoSuchEntityException {
//
//        jiraSprintsService.delete(id);
//
//        return new ResponseEntity<>(new JiraSprintDto(), HttpStatus.OK);
//    }
}
