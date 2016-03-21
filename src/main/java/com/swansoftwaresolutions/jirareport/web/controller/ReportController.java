package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard.*;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.IssuesByDayDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.SprintIssueListDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.FullSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.core.service.SprintIssueService;
import com.swansoftwaresolutions.jirareport.core.service.SprintService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.web.controller.helper.HelperMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Vitaliy Holovko
 */

@RestController
@RequestMapping("/rest")
public class ReportController {

    private ReportService reportService;

    private SprintService sprintService;

    private SprintIssueService sprintIssueService;

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    @Autowired
    public ReportController(ReportService reportService, SprintService sprintService, SprintIssueService sprintIssueService) {
        this.reportService = reportService;
        this.sprintService = sprintService;
        this.sprintIssueService = sprintIssueService;
    }

    @RequestMapping(value = "/v1/reports", method = RequestMethod.GET)
    private ResponseEntity<ReportListDto> getAllReports() {
        ReportListDto reportDtos = reportService.retrieveAllReportsList();
        return new ResponseEntity<>(reportDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/reports", method = RequestMethod.POST)
    private ResponseEntity<ReportDto> addReport(@Valid @RequestBody NewReportDto newReportDto) throws NoSuchEntityException {
        ReportDto reportDto = reportService.add(newReportDto);

        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/reports/{id}", method = RequestMethod.GET)
    private ResponseEntity<ReportDto> getReportById(@PathVariable("id") long id) throws NoSuchEntityException {
        ReportDto reportDto = reportService.retrieveReportByID(id);

        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }


    @RequestMapping(value = "/v1/reports/{id}", method = RequestMethod.PUT)
    private ResponseEntity<ReportDto> updateReport(@PathVariable("id") long id, @Valid @RequestBody NewReportDto reportDto) throws NoSuchEntityException {
        ReportDto updatedReport = reportService.update(reportDto, id);

        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }


    @RequestMapping(value = "/v1/reports/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<ReportDto> deleteReport(@PathVariable("id") long id) throws NoSuchEntityException {

        reportService.delete(id);

        return new ResponseEntity<>(new ReportDto(), HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/reports/closed", method = RequestMethod.GET)
    private ResponseEntity<ReportListDto> getAllClosedReports() {
        ReportListDto reportDtos = reportService.retrieveAllClosedReportsList();
        return new ResponseEntity<>(reportDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/reports/ongoing", method = RequestMethod.GET)
    private ResponseEntity<ReportListDto> getAllOngoingReports() {
        ReportListDto reportDtos = reportService.retrieveAllOngoingReportsList();
        return new ResponseEntity<>(reportDtos, HttpStatus.OK);
    }


    @RequestMapping(value = "/v1/reports/{report_id}/copy", method = RequestMethod.GET)
    private ResponseEntity<ReportDto> copyReport(@PathVariable("report_id") long id) throws NoSuchEntityException {
        ReportDto reportDto = reportService.copy(id);

        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }

    @RequestMapping(value = "v1/reports/{id}/data_with_sprints_and_teams", method = RequestMethod.GET)
    private ResponseEntity<ProjectDashboardDto> dataWithSprintsAndTeams(@PathVariable("id") Long id) {
        ProjectDashboardDto projectDashboardDto = reportService.findProjectDashboard(id);
        return new ResponseEntity<>(projectDashboardDto, HttpStatus.OK);
    }

}