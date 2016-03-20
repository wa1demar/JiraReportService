package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard.Chart;
import com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard.ProjectDashboardDto;
import com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard.ProjectReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard.SprintProjectReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDtos;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.core.service.SprintService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@RestController
@RequestMapping("/rest")
public class ReportController {

    private ReportService reportService;

    private SprintService sprintService;

    @Autowired
    public ReportController(ReportService reportService, SprintService sprintService) {
        this.reportService = reportService;
        this.sprintService = sprintService;
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
        ProjectDashboardDto projectDashboardDto = new ProjectDashboardDto();
        projectDashboardDto.setReport(buildProjectReport(id));
        if (projectDashboardDto.getReport().getBoardId() == null) {
            projectDashboardDto.setSprints(buildProjectSprintsByBoardId(projectDashboardDto.getReport().getId()));
        } else {
            projectDashboardDto.setSprints(buildProjectSprintsByBoardId(projectDashboardDto.getReport().getId()));
        }
        return new ResponseEntity<>(projectDashboardDto, HttpStatus.OK);
    }

    private List<SprintProjectReportDto> buildProjectSprintsByBoardId(Long reportId) {
        List<SprintProjectReportDto> sprints = new ArrayList<>();
        SprintDtos sprintIssueDto = new SprintDtos();
        try {
            sprintIssueDto = sprintService.findByReportId(reportId);

            for (SprintDto sprintDto : sprintIssueDto.getSprints()) {
                SprintProjectReportDto sprintProj = new SprintProjectReportDto();
                sprintProj.setId(sprintDto.getId());
                sprintProj.setName(sprintDto.getName());
                sprintProj.setNotCountTarget(sprintDto.isNotCountTarget());
                sprintProj.setShowUat(sprintDto.isShowUat());
                sprintProj.setState(sprintDto.getState());
                sprintProj.setType(sprintDto.getType());
                sprintProj.setStartDate(sprintDto.getStartDate());
                sprintProj.setEndDate(sprintDto.getEndDate());
                sprintProj.setCompleteDate(sprintDto.getEndDate());

                sprintProj.setTargetPoints(0);
                sprintProj.setTargetHours(0L);
                sprintProj.setTargetQatDefectHours(0L);
                sprintProj.setTargetQatDefectMin(0);
                sprintProj.setTargetQatDefectMax(0);
                sprintProj.setTargetUatDefectHours(0L);
                sprintProj.setTargetUatDefectMin(0);
                sprintProj.setTargetUatDefectMax(0);

                sprintProj.setActualHours(0L);
                sprintProj.setActualPoints(0);
                sprintProj.setActualQatDefectHours(0L);
                sprintProj.setActualQatDefectPoints(0);
                sprintProj.setActualUatDefectHours(0L);
                sprintProj.setActualUatDefectPoints(0);

                Chart chart = new Chart();
                chart.setLabel(new String[]{"hello", "foo", "bar"});
                chart.setActual(new int[]{1, 2, 3});
                chart.setTarget(new int[]{3, 2, 1});

                sprintProj.setChart(chart);

                sprints.add(sprintProj);
            }
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        return sprints;
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
            chart.setLabel(new String[]{"hello", "foo", "bar"});
            chart.setActual(new int[]{1, 2, 3});
            chart.setTarget(new int[]{3, 2, 1});

            projectReportDto.setChart(chart);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        return projectReportDto;
    }

}