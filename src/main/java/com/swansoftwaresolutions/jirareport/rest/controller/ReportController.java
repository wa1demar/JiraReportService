package com.swansoftwaresolutions.jirareport.rest.controller;

import com.swansoftwaresolutions.jirareport.core.entity.Report;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.ProjectMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.core.services.ReportService;
import com.swansoftwaresolutions.jirareport.rest.dto.InfoForNewReport;
import com.swansoftwaresolutions.jirareport.rest.dto.NewReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

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
    private ReportService reportService;
    private ReportMapper reportMapper;

    @Autowired
    public ReportController(ProjectService projectService, JiraUserService jiraUserService, ProjectMapper projectMapper, JiraUserMapper jiraUserMapper, ReportService reportService, ReportMapper reportMapper) {
        this.projectService = projectService;
        this.jiraUserService = jiraUserService;
        this.projectMapper = projectMapper;
        this.jiraUserMapper = jiraUserMapper;
        this.reportService = reportService;
        this.reportMapper = reportMapper;
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

    @RequestMapping(value = "/rest/report/create", method = RequestMethod.POST)
    private ResponseEntity<Boolean> addNewReport(@Valid @RequestBody NewReportDto newReportDto) {
        Report report = new Report();
        report.setTitle(newReportDto.getTitle());
        report.setTypeId(newReportDto.getTypeId());
        report.setCreator(newReportDto.getCreator());
        //ToDo Add createrId and board
        report.setCreatedDate(new Date());

        report = reportService.save(report);

        if (report!=null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
        }

    }




}