package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.dashboard.*;
import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Vitaliy Holovko
 */

@RestController
@RequestMapping("/rest")
public class ReportController {

    private ReportService reportService;


    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
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

    @RequestMapping(value = "/v1/reports/closed/{page}", method = RequestMethod.GET)
    private ResponseEntity<ReportListDto> getAllClosedReportsPaginated(@PathVariable("page") int page) {
        ReportListDto reportDtos = reportService.retrieveAllClosedReportsListPaginated(page);
        return new ResponseEntity<>(reportDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/reports/ongoing", method = RequestMethod.GET)
    private ResponseEntity<ReportListDto> getAllOngoingReports() {
        ReportListDto reportDtos = reportService.retrieveAllOngoingReportsList();
        return new ResponseEntity<>(reportDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/reports/ongoing/{page}", method = RequestMethod.GET)
    private ResponseEntity<ReportListDto> getAllOngoingReportsPaginated(@PathVariable("page") int page) {
        ReportListDto reportDtos = reportService.retrieveAllOngoingReportsListPaginated(page);
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