package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.ReportIdDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintsDto;
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
@RequestMapping("/rest")
public class SprintController {

    @Autowired
    JiraSprintsService jiraSprintsService;

    @RequestMapping(value = "/v1/sprints", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SprintsDto> retrieveByReportId(@Valid @RequestBody ReportIdDto reportIdDto) {
        SprintsDto sprintsDto = jiraSprintsService.retrieveByReportId(reportIdDto.getReportId(), reportIdDto.getTypeId());
        return new ResponseEntity<>(sprintsDto,HttpStatus.OK);
    }
}
