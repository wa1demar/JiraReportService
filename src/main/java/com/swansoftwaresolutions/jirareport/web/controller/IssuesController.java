package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.jira_issue.DueDatesDto;
import com.swansoftwaresolutions.jirareport.core.service.DueDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest")
public class IssuesController {

    @Autowired
    DueDateService dueDateService;

    @RequestMapping(value = "/v1/issues/due_dates/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<DueDatesDto> getAllBoards(@PathVariable("page") int page) {

        DueDatesDto dueDatesDto = dueDateService.retrieveDueDates(page);

        return new ResponseEntity<>(dueDatesDto, HttpStatus.OK);
    }
}
