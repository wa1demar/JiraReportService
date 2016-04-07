package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.jira_issue.DueDatesDto;
import com.swansoftwaresolutions.jirareport.core.service.DueDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest")
public class IssuesController {

    @Autowired
    DueDateService dueDateService;

    @RequestMapping(value = "/v1/issues/due_dates", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<DueDatesDto> getAllBoards() {

        DueDatesDto dueDatesDto = dueDateService.retrieveDueDates();

        return new ResponseEntity<>(dueDatesDto, HttpStatus.OK);
    }
}
