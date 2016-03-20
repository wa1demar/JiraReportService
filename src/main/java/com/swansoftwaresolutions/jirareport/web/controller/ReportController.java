package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard.Chart;
import com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard.ProjectDashboardDto;
import com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard.ProjectReportDto;
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
    private ResponseEntity<ProjectDashboardDto> dataWithSprintsAndTeams(@PathVariable("id") Long id){
        ProjectDashboardDto projectDashboardDto = new ProjectDashboardDto();
        projectDashboardDto.setReport(buildProjectReport(id));
        
        return new ResponseEntity<ProjectDashboardDto>(projectDashboardDto, HttpStatus.OK);
    }

    private ProjectReportDto buildProjectReport(Long id) {
        ProjectReportDto projectReportDto = new ProjectReportDto();
        try {
            ReportDto reportDto = reportService.retrieveReportByID(id);
            projectReportDto.setId(reportDto.getId());
            projectReportDto.setTitle(reportDto.getTitle());
            projectReportDto.setCreator(reportDto.getCreator());
            projectReportDto.setBoardId(reportDto.getBoardId());
            projectReportDto.setBoardName(reportDto.getBoardName());
            projectReportDto.setCreatedDate(reportDto.getCreatedDate());
            projectReportDto.setUpdatedDate(reportDto.getUpdatedDate());
            projectReportDto.setClosedDate(reportDto.getClosedDate());
            projectReportDto.setTypeId(reportDto.getTypeId());
            projectReportDto.setClosed(reportDto.isClosed());
            projectReportDto.setAdmins(reportDto.getAdmins());

            projectReportDto.setTargetPoints(0);
            projectReportDto.setTargetHours(0L);
            projectReportDto.setTargetQatDefectHours(0L);
            projectReportDto.setTargetQatDefectMin(0);
            projectReportDto.setTargetQatDefectMax(0);
            projectReportDto.setTargetUatDefectHours(0L);
            projectReportDto.setTargetUatDefectMin(0);
            projectReportDto.setTargetUatDefectMax(0);

            projectReportDto.setActualHours(0L);
            projectReportDto.setActualPoints(0);
            projectReportDto.setActualQatDefectHours(0L);
            projectReportDto.setActualQatDefectPoints(0);
            projectReportDto.setActualUatDefectHours(0L);
            projectReportDto.setActualUatDefectPoints(0);

            Chart chart = new Chart();
            chart.setLabel(new String[]{ "hello", "foo", "bar" });
            chart.setActual(new int[]{ 1,2,3 });
            chart.setTarget(new int[]{ 3,2,1 });

            projectReportDto.setChart(chart);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        return projectReportDto;
    }

}