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

}
