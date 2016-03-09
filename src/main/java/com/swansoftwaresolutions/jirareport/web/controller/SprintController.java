package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.service.JiraSprintsService;
import com.swansoftwaresolutions.jirareport.rest.dto.SprintDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.SprintsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest")
public class SprintController {

    @Autowired
    JiraSprintsService jiraSprintsService;

    @RequestMapping(value = "/v1/sprints", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SprintsDto> retrieveByReportId() {
        return new ResponseEntity<>(jiraSprintsService.retrieveByReportId(reportId), HttpStatus.OK);
    }
}
