package com.swansoftwaresolutions.jirareport.web.controller;


import com.swansoftwaresolutions.jirareport.core.dto.SprintTeamsDto;
import com.swansoftwaresolutions.jirareport.core.service.SprintTeamService;
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
public class SprintTeamController  {

    @Autowired
    SprintTeamService sprintTeamService;

    @RequestMapping(value = "/v1/sprint_teams", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SprintTeamsDto> getAll() {
        return new ResponseEntity<>(sprintTeamService.getAll(), HttpStatus.OK);
    }
}
