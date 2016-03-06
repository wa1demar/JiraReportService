package com.swansoftwaresolutions.jirareport.rest.controller;

import com.swansoftwaresolutions.jirareport.core.service.JiraBoardService;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.rest.dto.InfoForNewReportDto;
import com.swansoftwaresolutions.jirareport.rest.dto.NewReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Vitaliy Holovko
 */

@RestController
public class ReportController {

    private JiraUserService jiraUserService;
    private ReportService reportService;
    private JiraBoardService jiraBoardService;

    @Autowired
    public ReportController(JiraUserService jiraUserService, ReportService reportService, JiraBoardService jiraBoardService) {
        this.jiraUserService = jiraUserService;
        this.reportService = reportService;
        this.jiraBoardService = jiraBoardService;
    }

    @RequestMapping(value = "/rest/report/datainfo", method = RequestMethod.GET)
    private ResponseEntity<InfoForNewReportDto> listResponseEntity() {
        return new ResponseEntity<>(prepareListsOfProjectsAndUsers(), HttpStatus.OK);
    }

    private InfoForNewReportDto prepareListsOfProjectsAndUsers() {
        InfoForNewReportDto infoForNewReport = new InfoForNewReportDto();
        infoForNewReport.boards = jiraBoardService.findAllBoardForInfo();
        infoForNewReport.users = jiraUserService.findAll();

        return infoForNewReport;
    }

    @RequestMapping(value = "/rest/report/create", method = RequestMethod.POST)
    private ResponseEntity<NewReportDto> addNewReport(@Valid @RequestBody NewReportDto newReportDto) {
        NewReportDto reportDto = reportService.save(newReportDto);

        if (reportDto!=null) {
            return new ResponseEntity<>(reportDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(reportDto, HttpStatus.NO_CONTENT);
        }

    }




}