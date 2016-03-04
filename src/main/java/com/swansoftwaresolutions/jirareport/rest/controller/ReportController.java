package com.swansoftwaresolutions.jirareport.rest.controller;

import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.ProjectMapper;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.rest.dto.InfoForNewReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */

@RestController
public class ReportController {

    private ProjectService projectService;
    private JiraUserService jiraUserService;
    private ProjectMapper projectMapper;
    private JiraUserMapper jiraUserMapper;

    @Autowired
    public ReportController(ProjectService projectService, JiraUserService jiraUserService, ProjectMapper projectMapper, JiraUserMapper jiraUserMapper) {
        this.projectService = projectService;
        this.jiraUserService = jiraUserService;
        this.projectMapper = projectMapper;
        this.jiraUserMapper = jiraUserMapper;
    }

    @RequestMapping(value = "/rest/infofornewreport", method = RequestMethod.GET)
    private ResponseEntity<InfoForNewReport> listResponseEntity() {
        return new ResponseEntity<>(prepareListsOfProjectsAndUsers(), HttpStatus.OK);
    }
    private InfoForNewReport prepareListsOfProjectsAndUsers() {
        InfoForNewReport infoForNewReport = new InfoForNewReport();
        infoForNewReport.projects = projectMapper.toDtos(projectService.getAllProjects());
        infoForNewReport.users = jiraUserMapper.toDtos(jiraUserService.findAll());

        return infoForNewReport;
    }


}
