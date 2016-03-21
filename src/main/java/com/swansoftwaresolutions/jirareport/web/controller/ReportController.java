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
        ProjectDashboardDto projectDashboardDto = new ProjectDashboardDto();
        projectDashboardDto.setReport(buildProjectReport(id));
        if (projectDashboardDto.getReport().getBoardId() == null) {
            projectDashboardDto.setSprints(buildManualSprints(projectDashboardDto.getReport().getId()));
        } else {
            projectDashboardDto.setSprints(buildManualSprints(projectDashboardDto.getReport().getId()));
        }
        return new ResponseEntity<>(projectDashboardDto, HttpStatus.OK);
    }

    private List<SprintProjectReportDto> buildManualSprints(Long reportId) {
        List<SprintProjectReportDto> sprints = new ArrayList<>();

        List<FullSprintDto> sprintDtoList;
        try {
            sprintDtoList = sprintService.findByReportId(reportId);

            for (FullSprintDto sprintDto : sprintDtoList) {
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

                List<SprintTeamDto> sprintTeamDtoList = convertData(sprintDto.getDevelopers());
                sprintProj.setSprintTeam(sprintTeamDtoList);

                if (sprintTeamDtoList!=null) {

                    for (SprintTeamDto teamDto : sprintTeamDtoList) {
                        sprintProj.setTargetPoints(sprintProj.getTargetPoints()+teamDto.getTargetPoints());
                        sprintProj.setTargetHours(0L);//TODO
                        sprintProj.setTargetQatDefectHours(isNull(sprintProj.getTargetQatDefectHours())+teamDto.getDefectTargetHours());
                        sprintProj.setTargetQatDefectMin(0);
                        sprintProj.setTargetQatDefectMax(0);
                        sprintProj.setTargetUatDefectHours(isNull(sprintProj.getTargetQatDefectHours())+teamDto.getUatDefectTargetHours());
                        sprintProj.setTargetUatDefectMin(sprintProj.getTargetUatDefectMin()+teamDto.getUatDefectMin());
                        sprintProj.setTargetUatDefectMax(sprintProj.getTargetUatDefectMax()+teamDto.getUatDefectMax());

                        sprintProj.setActualHours(0L);
                        sprintProj.setActualPoints(sprintProj.getActualPoints()+teamDto.getActualPoints());
                        sprintProj.setActualQatDefectHours(0L);
                        sprintProj.setActualQatDefectPoints(0);
                        sprintProj.setActualUatDefectHours(isNull(sprintProj.getActualUatDefectHours())+teamDto.getUatDefectTargetHours());
                        sprintProj.setActualUatDefectPoints(0);
                    }
                } else {
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
                }

                sprintProj.setChart(getChatData(sprintDto));

                sprints.add(sprintProj);
            }
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        return sprints;
    }

    private Long isNull(Long hours) {
        if (hours == null){
            return 0L;
        }
        return hours;
    }

    private Chart getChatData(FullSprintDto sprintDto) {
        Chart chart = new Chart();

        List<IssuesByDayDto> issuesByDayList = getIssueByDay(sprintDto);

        String date = "0,";
        for (IssuesByDayDto issuesByDayDto : issuesByDayList){
            date+=issuesByDayDto.getDate()+",";
        }

        String[] dateArray =date.split(",");
        chart.setLabel(dateArray);
        int[] actual = new int[dateArray.length];
        int[] target = new int[dateArray.length];

        for (IssuesByDayDto issuesDto : issuesByDayList){
            for (SprintIssueDto sprintIssueDto :issuesDto.getIssues()) {
                actual[0] = +sprintIssueDto.getPoint();
                target[0] = +sprintIssueDto.getPoint();
            }
        }

        for (int i=1; i<dateArray.length; i++){
            for (IssuesByDayDto issuesDto : issuesByDayList){
                if (issuesDto.getDate().equals(dateArray[i])) {
                    for (SprintIssueDto sprintIssueDto : issuesDto.getIssues()) {
                        actual[i] = -sprintIssueDto.getPoint();
                        target[i] = -sprintIssueDto.getPoint();
                    }
                }
            }

        }
        chart.setActual(actual);
        chart.setTarget(target);

        return chart;
    }

    private List<IssuesByDayDto> getIssueByDay(FullSprintDto sprintDto) {
        List<IssuesByDayDto> results = new ArrayList<>();

        long oneDayMilSec = 86400000; // number of milliseconds in one day

        SprintIssueListDto printIssueList = sprintIssueService.findBySprintId(sprintDto.getId());
        Date startDate = sprintDto.getStartDate();
        Date endDate = sprintDto.getEndDate();

        long startDateMilSec = startDate.getTime();
        long endDateMilSec = endDate.getTime();

        for(long d=startDateMilSec; d<=endDateMilSec; d=d+oneDayMilSec){
            List<SprintIssueDto> issues = new ArrayList<>();
            Date date1 = new Date();
            Date date2 = new Date();
            for (SprintIssueDto sprintIssueDto: printIssueList.getSprintIssueDtos()){
                try {
                    date1 = new Date(d);
                    date2 = sdf.parse(sprintIssueDto.getIssueDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (isSameDate(date1,date2)){
                    issues.add(sprintIssueDto);
                }
            }

            IssuesByDayDto issuesByDayDto = new IssuesByDayDto();
            issuesByDayDto.setDate(sdf.format(d));
            issuesByDayDto.setIssues(issues);

            results.add(issuesByDayDto);
        }

        return results;
    }

    public  boolean isSameDate(Date date, Date anotherDate) {
        if(date==null && anotherDate==null){
            return true;
        }
        else if(date==null || anotherDate==null){
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar anotherCalendar = Calendar.getInstance();
        anotherCalendar.setTime(anotherDate);
        anotherCalendar.set(Calendar.HOUR_OF_DAY, 0);
        anotherCalendar.set(Calendar.MINUTE, 0);
        anotherCalendar.set(Calendar.SECOND, 0);
        anotherCalendar.set(Calendar.MILLISECOND, 0);
        anotherCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        return calendar.compareTo(anotherCalendar) == 0;
    }

    private List<SprintTeamDto> convertData(List<SprintDeveloperDto> developers) {
        List<SprintTeamDto> list = new ArrayList<>();
        for (SprintDeveloperDto dev :developers){
            SprintTeamDto team =new SprintTeamDto();
            team.setDevName(dev.getDeveloperName());
            team.setEngineerLevel(dev.getEngineerLevel());
            team.setParticipationLevel(dev.getParticipationLevel());
            team.setDaysInSprint(dev.getDaysInSprint());
            team.setTargetPoints(dev.getTargetPoints());
            team.setActualPoints(0);
            team.setDefectMin(0);//TODO
            team.setDefectMax(0);//TODO
            team.setDefectActual(0);
            team.setDefectTargetHours(0L);
            team.setDefectActualHours(0L);
            team.setUatDefectMax(0);//TODO
            team.setUatDefectMin(0);//TODO
            team.setUatDefectActual(0);
            team.setUatDefectActualHours(0L);
            team.setUatDefectTargetHours(0L);

            list.add(team);

        }
        return list;
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