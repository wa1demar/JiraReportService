package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard.*;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.IssuesByDayDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.SprintIssueListDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDtoBuilder;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.FullSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.core.service.SprintIssueService;
import com.swansoftwaresolutions.jirareport.core.service.SprintService;
import com.swansoftwaresolutions.jirareport.domain.entity.*;
import com.swansoftwaresolutions.jirareport.domain.entity.builder.ReportBuilder;
import com.swansoftwaresolutions.jirareport.domain.repository.*;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.web.controller.helper.HelperMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Vitaliy Holovko
 */

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    JiraSprintRepository jiraSprintRepository;

    @Autowired
    SprintDeveloperRepository sprintDeveloperRepository;

    @Autowired
    JiraUserRepository jiraUserRepository;

    @Autowired
    JiraBoardRepository jiraBoardRepository;

    @Autowired
    SprintRepository sprintRepository;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    SprintService sprintService;

    @Autowired
    SprintIssueService sprintIssueService;


    @Override
    public ReportListDto retrieveAllReportsList() {
        List<ReportDto> reportDtos = reportMapper.toDtos(reportRepository.findAll());

        for (ReportDto dto : reportDtos) {
            if (dto.getBoardId() != null) {
                JiraBoard board = jiraBoardRepository.findById(dto.getBoardId());
                dto.setBoardName(board.getName());
                dto.setJiraBoardId(board.getBoardId());
            }
        }

        return new ReportListDtoBuilder().reportsDto(reportDtos).build();
    }

    @Override
    public ReportDto add(NewReportDto newReportDto) throws NoSuchEntityException {

        List<JiraUser> jiraUsers = null;
        if (newReportDto.getAdmins() != null && newReportDto.getAdmins().length > 0) {
            jiraUsers = jiraUserRepository.findByLogins(newReportDto.getAdmins());
        }

        Report newReport = reportMapper.fromDto(newReportDto);
        newReport.setAdmins(jiraUsers);

        Report addedReport = reportRepository.add(newReport);

        List<JiraSprint> jiraSprints = jiraSprintRepository.findByBoardId(addedReport.getBoardId());

        for (JiraSprint jiraSprint : jiraSprints) {
            Sprint sprint = new Sprint();
            sprint.setJiraSprint(jiraSprint);
            sprint.setReport(addedReport);

            sprintRepository.add(sprint);
        }

        ReportDto reportDto = reportMapper.toDto(addedReport);
        if (reportDto.getBoardId() != null) {
            JiraBoard board = jiraBoardRepository.findById(reportDto.getBoardId());
            reportDto.setBoardName(board.getName());
            reportDto.setJiraBoardId(board.getBoardId());
        }

        return reportDto;
    }

    @Override
    public ReportDto retrieveReportByID(long id) throws NoSuchEntityException {
        ReportDto rDto = reportMapper.toDto(reportRepository.findById(id));
        if (rDto.getBoardId() != null) {
            JiraBoard board = jiraBoardRepository.findById(rDto.getBoardId());
            rDto.setBoardName(board.getName());
            rDto.setJiraBoardId(board.getBoardId());
        }

        return rDto;
    }

    @Override
    public ReportListDto retrieveAllClosedReportsList() {
        List<ReportDto> reportDtos = reportMapper.toDtos(reportRepository.findAllClosed());

        for (ReportDto dto : reportDtos) {
            if (dto.getBoardId() != null) {
                JiraBoard board = jiraBoardRepository.findById(dto.getBoardId());
                dto.setBoardName(board.getName());
                dto.setJiraBoardId(board.getBoardId());
            }
        }

        return new ReportListDtoBuilder().reportsDto(reportDtos).build();
    }

    @Override
    public ReportDto copy(long id) throws NoSuchEntityException {

        Report report = reportRepository.findById(id);

        List<String> adminLogins = report.getAdmins().stream().map(sd -> sd.getLogin()).collect(Collectors.toList());
        String[] adminLoginArray = new String[adminLogins.size()];
        List<JiraUser> admin = jiraUserRepository.findByLogins(adminLogins.toArray(adminLoginArray));

        Report newReport = new ReportBuilder()
                .title("Copy of " + report.getTitle())
                .creator(report.getCreator())
                .boardId(report.getBoardId())
                .isClosed(report.getClosed())
                .admins(admin)
                .closedDate(report.getClosedDate())
                .createdDate(report.getCreatedDate())
                .syncDate(report.getSyncDate())
                .typeId(report.getTypeId())
                .build();

        Report addedReport = reportRepository.add(newReport);

        List<Sprint> targetSprints = sprintRepository.findByReportId(id);

        for (Sprint target : targetSprints) {
            Sprint sprint = new Sprint();
            sprint.setJiraSprint(target.getJiraSprint());
            sprint.setReport(addedReport);
            sprint.setNotCountTarget(target.isNotCountTarget());
            sprint.setShowUAT(target.isShowUAT());
            sprint.setType(target.getType());

            List<SprintDeveloper> developers = sprintDeveloperRepository.findBySprintId(target.getId());

            Sprint newSprint = sprintRepository.add(sprint);

            for (SprintDeveloper developer : developers) {
                developer.setId(null);
                developer.setSprint(newSprint);

                sprintDeveloperRepository.add(developer);
            }
        }


        return reportMapper.toDto(addedReport);
    }

    @Override
    public ReportDto update(NewReportDto newReportDto, long id) throws NoSuchEntityException {
        List<JiraUser> jiraUsers = null;
        if (newReportDto.getAdmins() != null && newReportDto.getAdmins().length > 0) {
            jiraUsers = jiraUserRepository.findByLogins(newReportDto.getAdmins());
        } else {
            jiraUsers = new ArrayList<>();
        }

        Report report = reportMapper.fromDto(newReportDto);
        report.setId(id);
        report.setAdmins(jiraUsers);

        return reportMapper.toDto(reportRepository.update(report));
    }

    @Override
    public void delete(ReportDto reportDto) throws NoSuchEntityException {
        reportRepository.delete(reportMapper.fromDto(reportDto));
    }

    @Override
    public ReportDto delete(long id) throws NoSuchEntityException {
        sprintRepository.deleteByReportId(id);
        return reportMapper.toDto(reportRepository.delete(id));
    }

    @Override
    public ReportDto findByBoardId(Long boardId) throws NoSuchEntityException {
        return reportMapper.toDto(reportRepository.findByBoardId(boardId));
    }

    @Override
    public ReportListDto retrieveAllOngoingReportsList() {
        List<ReportDto> reportDtos = reportMapper.toDtos(reportRepository.findAllOpened());

        for (ReportDto dto : reportDtos) {
            if (dto.getBoardId() != null) {
                JiraBoard board = jiraBoardRepository.findById(dto.getBoardId());
                dto.setBoardName(board.getName());
                dto.setJiraBoardId(board.getBoardId());
            }
        }

        return new ReportListDtoBuilder().reportsDto(reportDtos).build();
    }

    @Override
    public ProjectDashboardDto findProjectDashboard(Long id) {
        ProjectDashboardDto projectDashboardDto = new ProjectDashboardDto();
        projectDashboardDto.setReport(buildProjectReport(id));
        if (projectDashboardDto.getReport().getBoardId() == null) {
            projectDashboardDto.setSprints(buildManualSprints(projectDashboardDto.getReport().getId()));
        } else {
            projectDashboardDto.setSprints(buildManualSprints(projectDashboardDto.getReport().getId()));
        }
        return projectDashboardDto;
    }

    private List<SprintProjectReportDto> buildManualSprints(Long reportId) {

        HelperMethods helperMethods = new HelperMethods();

        List<SprintProjectReportDto> sprints = new ArrayList<>();

        List<FullSprintDto> sprintDtoList;
        try {
            sprintDtoList = sprintService.findByReportId(reportId);

            for (FullSprintDto sprintDto : sprintDtoList) {

                List<IssuesByDayDto> issuesByDayList = getIssueByDay(sprintDto);

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

                List<SprintDeveloperDto> sprintDevList = sprintDto.getDevelopers();
//                sprintProj.setSprintTeam(sprintDevList);

                if (sprintDevList != null) {
                    List<SprintDeveloperDto> sprintDevelopers = new ArrayList<>();
                    for (SprintDeveloperDto developerDto : sprintDevList) {
                        Set<SprintIssueDto> issuesSet = new HashSet<>();
                        for (IssuesByDayDto issuesByDayDto : issuesByDayList) {
                            for (SprintIssueDto issue : issuesByDayDto.getIssues()) {
                                issuesSet.add(issue);
                            }
                        }

                        for (SprintIssueDto issue : issuesSet) {
                            if (developerDto.getDeveloperLogin().equals(issue.getAssignee())) {
                                if (issue.getTypeName().equals("Story")) {
                                    developerDto.setActualPoints(developerDto.getActualPoints() + issue.getPoint());
                                } else if (issue.getTypeName().equals("QAT Defect")) {
                                    developerDto.setDefectActual(developerDto.getDefectActual()+1);
                                    developerDto.setDefectActualHours(helperMethods.isNull(developerDto.getDefectActualHours()) + (long)issue.getHours());
                                    developerDto.setTargetHours(helperMethods.isNull(developerDto.getTargetHours())+ (long)issue.getHours());
                                } else if (issue.getTypeName().equals("UAT Defect")) {
                                    developerDto.setUatDefectHours(helperMethods.isNull(developerDto.getDefectHours()) + (long)issue.getHours());
                                    developerDto.setUatDefectActualHours(helperMethods.isNull(developerDto.getUatDefectActualHours())+(long) issue.getHours());
                                    developerDto.setUatDefectActual(helperMethods.isNull(developerDto.getUatDefectActual())+1);
                                }
                            }
                        }

                        developerDto.setDefectTargetHours(helperMethods.isNull(developerDto.getDefectHours().longValue()));
                        developerDto.setDefectActualHours(helperMethods.isNull(developerDto.getDefectActualHours()));

                        developerDto.setUatDefectTargetHours(helperMethods.isNull(developerDto.getUatDefectHours().longValue()));
                        developerDto.setUatDefectActualHours(helperMethods.isNull(developerDto.getUatDefectActualHours()));
                        developerDto.setUatDefectActual(helperMethods.isNull(developerDto.getUatDefectActual()));

                        sprintDevelopers.add(developerDto);
                    }

                    sprintProj.setSprintTeam(sprintDevelopers);
                    for (SprintDeveloperDto dev : sprintDevelopers) {
                        sprintProj.setTargetPoints(sprintProj.getTargetPoints() + dev.getTargetPoints());
                        sprintProj.setActualQatDefectPoints(sprintProj.getActualQatDefectPoints()+dev.getDefectActual());
                        sprintProj.setTargetQatDefectHours(helperMethods.isNull(sprintProj.getTargetQatDefectHours())+dev.getDefectTargetHours());
                        sprintProj.setActualQatDefectHours(helperMethods.isNull(sprintProj.getActualQatDefectHours())+dev.getDefectActualHours());
                        sprintProj.setTargetQatDefectMin(sprintProj.getTargetQatDefectMin() + helperMethods.isNullDoubleToInt(dev.getDefectMin()));
                        sprintProj.setTargetQatDefectMax(sprintProj.getTargetQatDefectMax() + helperMethods.isNullDoubleToInt(dev.getDefectMax()));
                        if (sprintProj.isShowUat()) {
                            sprintProj.setActualUatDefectPoints(sprintProj.getActualUatDefectPoints()+helperMethods.isNull(dev.getUatDefectActual()));
                            sprintProj.setTargetUatDefectHours(helperMethods.isNull(sprintProj.getTargetUatDefectHours()) + helperMethods.isNull(dev.getUatDefectHours()));
                            sprintProj.setActualUatDefectHours(helperMethods.isNull(sprintProj.getActualUatDefectHours())+helperMethods.isNull(dev.getUatDefectActualHours()));
                            sprintProj.setTargetUatDefectMin(sprintProj.getTargetUatDefectMin() + helperMethods.isNullDoubleToInt(dev.getUatDefectMin()));
                            sprintProj.setTargetUatDefectMax(sprintProj.getTargetUatDefectMax() + helperMethods.isNullDoubleToInt(dev.getUatDefectMax()));
                        }

                        sprintProj.setActualPoints(sprintProj.getActualPoints() + dev.getActualPoints());

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

                sprintProj.setChart(getChatData(sprintDto, issuesByDayList));

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
            ReportDto reportDto = retrieveReportByID(id);
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
            chart.setLabel(new String[]{"0", "1", "2"});
            chart.setActual(new int[]{3, 2, 1});
            chart.setTarget(new int[]{3, 3, 0});

            projectReportDto.setChart(chart);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        return projectReportDto;
    }

    private Chart getChatData(FullSprintDto sprintDto, List<IssuesByDayDto> issuesByDayList) {
        Chart chart = new Chart();

        String date = "0,";
        for (IssuesByDayDto issuesByDayDto : issuesByDayList) {
            date += issuesByDayDto.getDate() + ",";
        }

        String[] dateArray = date.split(",");
        chart.setLabel(dateArray);
        int[] actual = new int[dateArray.length];
        int[] target = new int[dateArray.length];

        for (IssuesByDayDto issuesDto : issuesByDayList) {
            for (SprintIssueDto sprintIssueDto : issuesDto.getIssues()) {
                actual[0] = +sprintIssueDto.getPoint();
                target[0] = +sprintIssueDto.getPoint();
            }
        }

        for (int i = 1; i < dateArray.length; i++) {
            for (IssuesByDayDto issuesDto : issuesByDayList) {
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
        HelperMethods helperMethods = new HelperMethods();
        List<IssuesByDayDto> results = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");


        SprintIssueListDto sprintIssueListDto = sprintIssueService.findBySprintId(sprintDto.getId());

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(sprintDto.getStartDate());

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(sprintDto.getEndDate());

        while (!startDate.after(endDate)) {
            Date currentDate = startDate.getTime();

            List<SprintIssueDto> issues = new ArrayList<>();

            if (helperMethods.isWeekend(currentDate)) {
                startDate.add(Calendar.DATE, 1);
                continue;
            }

            for (SprintIssueDto sprintIssueDto : sprintIssueListDto.getSprintIssueDtos()) {
                Date date2 = null;
                try {
                    date2 = sdf.parse(sprintIssueDto.getIssueDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (helperMethods.isSameDate(currentDate, date2)) {
                    issues.add(sprintIssueDto);
                }
            }

            IssuesByDayDto issuesByDayDto = new IssuesByDayDto();
            issuesByDayDto.setDate(sdf.format(currentDate));
            issuesByDayDto.setIssues(issues);

            results.add(issuesByDayDto);

            startDate.add(Calendar.DATE, 1);
        }

        return results;
    }
}
