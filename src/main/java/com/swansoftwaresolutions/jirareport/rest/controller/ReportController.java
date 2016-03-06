package com.swansoftwaresolutions.jirareport.rest.controller;

import com.swansoftwaresolutions.jirareport.core.entity.Report;
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
import java.util.Date;

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

    @RequestMapping(value = "/rest/auth/infofornewreport", method = RequestMethod.GET)
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