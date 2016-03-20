package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.service.SprintIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vitaliy Holovko
 */
@RestController
@RequestMapping("/rest")
public class SprintIssueController {

    @Autowired
    SprintIssueService sprintIssueService;
}
