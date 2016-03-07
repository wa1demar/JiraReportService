package com.swansoftwaresolutions.jirareport.rest.controller;

import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraUsersDto;
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
public class JiraUsersController {

    @Autowired
    JiraUserService jiraUserService;

    @RequestMapping(value = "/v1/users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JiraUsersDto> getAllUsers() {
        return new ResponseEntity<>(jiraUserService.retrieveAllUsers(), HttpStatus.OK);
    }
}
