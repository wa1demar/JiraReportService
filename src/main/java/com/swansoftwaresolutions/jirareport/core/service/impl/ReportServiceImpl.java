package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.dashboard.*;
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
import com.swansoftwaresolutions.jirareport.domain.entity.builder.CacheProjectTotalBuilder;
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
    CacheProjectTotalRepository projectTotalRepository;

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
    public ProjectDashboardDto findProjectDashboard(Long reportId) {
        ProjectDashboardDto projectDashboardDto = new ProjectDashboardDto();

        projectDashboardDto.setReport(buildProjectReport(reportId));
        if (projectDashboardDto.getReport().getBoardId() == null) {
            projectDashboardDto.setSprints(buildManualSprints(projectDashboardDto.getReport().getId()));
        } else {
            projectDashboardDto.setSprints(buildManualSprints(projectDashboardDto.getReport().getId()));
        }
        projectDashboardDto.setReport(buildProjectReport(reportId, projectDashboardDto.getSprints()));
        return projectDashboardDto;
    }

    private List<SprintProjectReportDto> buildManualSprints(Long reportId) {

        HelperMethods help = new HelperMethods();

        List<SprintProjectReportDto> sprints = new ArrayList<>();

        List<FullSprintDto> sprintDtoList;
        try {
            sprintDtoList = sprintService.findByReportId(reportId);

            for (FullSprintDto sprintDto : sprintDtoList) {

                List<IssuesByDayDto> issuesByDayList = getIssueByDay(sprintDto);

                SprintProjectReportDto sprint = new SprintProjectReportDto();
                sprint.setId(sprintDto.getId());
                sprint.setName(sprintDto.getName());
                sprint.setNotCountTarget(sprintDto.isNotCountTarget());
                sprint.setShowUat(sprintDto.isShowUat());
                sprint.setState(sprintDto.getState());
                sprint.setType(sprintDto.getType());
                sprint.setStartDate(sprintDto.getStartDate());
                sprint.setEndDate(sprintDto.getEndDate());
                sprint.setCompleteDate(sprintDto.getEndDate());

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
                                if (issue.getStatusName().equals("Done")) {
                                    if (issue.getTypeName().equals("Story")) {
                                        developerDto.setActualPoints(developerDto.getActualPoints() + issue.getPoint());
                                        developerDto.setActualHours(help.isNull(developerDto.getActualHours()) + issue.getPoint());
                                    } else if (issue.getTypeName().equals("QAT Defect")) {
                                        developerDto.setDefectActual(developerDto.getDefectActual() + 1);
                                        developerDto.setDefectActualHours(help.isNull(developerDto.getDefectActualHours()) + (long) issue.getHours());
                                        developerDto.setTargetHours(help.isNull(developerDto.getTargetHours()) + (long) issue.getHours());
                                    } else if (issue.getTypeName().equals("UAT Defect")) {
                                        developerDto.setUatDefectHours(help.isNull(developerDto.getDefectHours()) + (long) issue.getHours());
                                        developerDto.setUatDefectActualHours(help.isNull(developerDto.getUatDefectActualHours()) + (long) issue.getHours());
                                        developerDto.setUatDefectActual(help.isNull(developerDto.getUatDefectActual()) + 1);
                                    }
                                }
                            }
                        }

                        developerDto.setDefectTargetHours(help.isNull(developerDto.getDefectHours().longValue()));
                        developerDto.setDefectActualHours(help.isNull(developerDto.getDefectActualHours()));

                        developerDto.setUatDefectTargetHours(help.isNull(developerDto.getUatDefectHours().longValue()));
                        developerDto.setUatDefectActualHours(help.isNull(developerDto.getUatDefectActualHours()));
                        developerDto.setUatDefectActual(help.isNull(developerDto.getUatDefectActual()));

                        sprintDevelopers.add(developerDto);
                    }

                    sprint.setSprintTeam(sprintDevelopers);
                    for (SprintDeveloperDto dev : sprintDevelopers) {
                        sprint.setTargetPoints(sprint.getTargetPoints() + dev.getTargetPoints());
                        sprint.setActualQatDefectPoints(sprint.getActualQatDefectPoints() + dev.getDefectActual());
                        sprint.setTargetQatDefectHours(help.isNull(sprint.getTargetQatDefectHours()) + dev.getDefectTargetHours());
                        sprint.setActualQatDefectHours(help.isNull(sprint.getActualQatDefectHours()) + dev.getDefectActualHours());
                        sprint.setTargetQatDefectMin(sprint.getTargetQatDefectMin() + help.isNullDoubleToInt(dev.getDefectMin()));
                        sprint.setTargetQatDefectMax(sprint.getTargetQatDefectMax() + help.isNullDoubleToInt(dev.getDefectMax()));
                        if (sprint.isShowUat()) {
                            sprint.setActualUatDefectPoints(sprint.getActualUatDefectPoints() + help.isNull(dev.getUatDefectActual()));
                            sprint.setTargetUatDefectHours(help.isNull(sprint.getTargetUatDefectHours()) + help.isNull(dev.getUatDefectHours()));
                            sprint.setActualUatDefectHours(help.isNull(sprint.getActualUatDefectHours()) + help.isNull(dev.getUatDefectActualHours()));
                            sprint.setTargetUatDefectMin(sprint.getTargetUatDefectMin() + help.isNullDoubleToInt(dev.getUatDefectMin()));
                            sprint.setTargetUatDefectMax(sprint.getTargetUatDefectMax() + help.isNullDoubleToInt(dev.getUatDefectMax()));
                        }

                        sprint.setActualPoints(sprint.getActualPoints() + dev.getActualPoints());
                        sprint.setActualHours(help.isNull(sprint.getActualHours()) + help.isNull(dev.getActualHours()));
                        sprint.setTargetHours(help.isNull(sprint.getTargetHours())+help.isNull(dev.getTargetHours()));
                    }
                } else {
                    sprint.setTargetPoints(0);
                    sprint.setTargetHours(0L);
                    sprint.setTargetQatDefectHours(0L);
                    sprint.setTargetQatDefectMin(0);
                    sprint.setTargetQatDefectMax(0);
                    sprint.setTargetUatDefectHours(0L);
                    sprint.setTargetUatDefectMin(0);
                    sprint.setTargetUatDefectMax(0);

                    sprint.setActualHours(0L);
                    sprint.setActualPoints(0);
                    sprint.setActualQatDefectHours(0L);
                    sprint.setActualQatDefectPoints(0);
                    sprint.setActualUatDefectHours(0L);
                    sprint.setActualUatDefectPoints(0);
                }

                sprint.setChart(getChatData(issuesByDayList, sprint.getTargetPoints(), sprint.getActualPoints(), checkVelosity(sprintDevList)));

                sprints.add(sprint);
            }
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        return sprints;
    }

    private int checkVelosity(List<SprintDeveloperDto> sprintDevList) {
        int velocity = 0;
        for (SprintDeveloperDto dev : sprintDevList) {
            velocity = +dev.getEngineerLevel().intValue();
        }
        return velocity;
    }

    private ProjectReportDto buildProjectReport(Long id, List<SprintProjectReportDto> sprints) {
        ProjectReportDto prRep = new ProjectReportDto();

        HelperMethods helpM = new HelperMethods();

        try {
            ReportDto reportDto = retrieveReportByID(id);
            prRep.setId(reportDto.getId());
            prRep.setTitle(reportDto.getTitle());
            prRep.setCreator(reportDto.getCreator());
            prRep.setBoardId(reportDto.getBoardId());
            prRep.setBoardName(reportDto.getBoardName());
            prRep.setCreatedDate(reportDto.getCreatedDate());
            prRep.setUpdatedDate(reportDto.getUpdatedDate());
            prRep.setClosedDate(reportDto.getClosedDate());
            prRep.setTypeId(reportDto.getTypeId());
            prRep.setClosed(reportDto.isClosed());
            prRep.setAdmins(reportDto.getAdmins());

            if (sprints != null) {
                for (SprintProjectReportDto sprint : sprints) {
                    if (!sprint.isNotCountTarget()) {
                        prRep.setTargetPoints(helpM.isNullFloat(prRep.getTargetPoints()) + helpM.isNullFloat(sprint.getTargetPoints()));
                        prRep.setTargetHours(helpM.isNull(prRep.getTargetHours()) + helpM.isNull(sprint.getTargetHours()));
                        prRep.setTargetQatDefectHours(helpM.isNull(prRep.getTargetQatDefectHours()) + helpM.isNull(sprint.getTargetQatDefectHours()));
                        prRep.setTargetQatDefectMin(prRep.getTargetQatDefectMin() + sprint.getTargetQatDefectMin());
                        prRep.setTargetQatDefectMax(prRep.getTargetQatDefectMax() + sprint.getTargetQatDefectMax());
                        prRep.setTargetUatDefectHours(helpM.isNull(prRep.getTargetUatDefectHours()) + helpM.isNull(sprint.getTargetUatDefectHours()));
                        prRep.setTargetUatDefectMin(prRep.getTargetUatDefectMin() + sprint.getTargetUatDefectMin());
                        prRep.setTargetUatDefectMax(prRep.getTargetUatDefectMax() + sprint.getTargetUatDefectMax());

                        prRep.setActualHours(helpM.isNull(prRep.getActualHours()) + helpM.isNull(sprint.getActualHours()));
                        prRep.setActualPoints(helpM.isNullFloat(prRep.getActualPoints()) + helpM.isNullFloat(sprint.getActualPoints()));
                        prRep.setActualQatDefectHours(helpM.isNull(prRep.getActualQatDefectHours()) + helpM.isNull(sprint.getActualQatDefectHours()));
                        prRep.setActualQatDefectPoints(helpM.isNullFloat(prRep.getActualQatDefectPoints()) + helpM.isNullFloat(sprint.getActualQatDefectPoints()));
                        prRep.setActualUatDefectHours(helpM.isNull(prRep.getActualUatDefectHours()) + helpM.isNull(sprint.getActualUatDefectHours()));
                        prRep.setActualUatDefectPoints(helpM.isNullFloat(prRep.getActualUatDefectPoints()) + helpM.isNullFloat(sprint.getActualUatDefectPoints()));
                    }
                }
            } else {
                prRep.setTargetPoints(0);
                prRep.setTargetHours(0L);
                prRep.setTargetQatDefectHours(0L);
                prRep.setTargetQatDefectMin(0);
                prRep.setTargetQatDefectMax(0);
                prRep.setTargetUatDefectHours(0L);
                prRep.setTargetUatDefectMin(0);
                prRep.setTargetUatDefectMax(0);

                prRep.setActualHours(0L);
                prRep.setActualPoints(0);
                prRep.setActualQatDefectHours(0L);
                prRep.setActualQatDefectPoints(0);
                prRep.setActualUatDefectHours(0L);
                prRep.setActualUatDefectPoints(0);
            }

            prRep.setChart(genersteReportChart(sprints, prRep.getTargetPoints(), prRep.getActualPoints()));

            projectTotalRepository.saveOrUpdate(new CacheProjectTotalBuilder()
                    .vTargetPoints(prRep.getTargetPoints())
                    .vActualPoints(prRep.getActualPoints())
                    .qtargetMin(prRep.getTargetQatDefectMin())
                    .qtargetMax(prRep.getTargetQatDefectMax())
                    .qActualPoints(prRep.getActualQatDefectPoints())
                    .qTargetHours(prRep.getTargetQatDefectHours())
                    .qActualHours(prRep.getActualQatDefectHours())
                    .utargetMin(prRep.getTargetUatDefectMin())
                    .utargetMax(prRep.getTargetUatDefectMax())
                    .uActualPoints(prRep.getActualUatDefectPoints())
                    .uTargetHours(prRep.getTargetUatDefectHours())
                    .uActualHours(prRep.getActualUatDefectHours())
                    .chartActual(Arrays.stream(prRep.getChart().getActual()).boxed().toArray(Integer[]::new))
                    .chartTarget(Arrays.stream(prRep.getChart().getTarget()).boxed().toArray(Integer[]::new))
                    .chartLabels(prRep.getChart().getLabel())
                    .report(reportMapper.fromDto(reportDto))
                    .vActualHours(prRep.getActualHours())
                    .vTargetHours(prRep.getTargetHours())
                    .build());

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        return prRep;
    }

    private Chart genersteReportChart(List<SprintProjectReportDto> sprints, float targetPoints, float actualPoints) {
        Chart chart = new Chart();

        HelperMethods helperMethods = new HelperMethods();

        String date = "0,";

        int key = 1;
        for (SprintProjectReportDto sprint : sprints) {
            if (!sprint.isNotCountTarget()) {
                date += key + ",";
                key++;
            }
        }

        String[] dateArray = date.split(",");
        chart.setLabel(dateArray);
        int[] actual = new int[dateArray.length];
        int[] target = new int[dateArray.length];

        actual[0] = (int) targetPoints;
        target[0] = (int) targetPoints;

        for (int i = 1; i < dateArray.length; i++) {
            for (SprintProjectReportDto sprint : sprints) {
                if (!sprint.isNotCountTarget()) {
                    actual[i] = actual[i - 1] - (int) sprint.getActualPoints();
                    target[i] = target[i - 1] - (int) sprint.getTargetPoints();
                }
            }
        }

        chart.setActual(actual);
        chart.setTarget(target);

        return chart;
    }

    private ProjectReportDto buildProjectReport(Long id) {
        ProjectReportDto prRep = new ProjectReportDto();
        try {
            ReportDto reportDto = retrieveReportByID(id);
            prRep.setId(reportDto.getId());
            prRep.setTitle(reportDto.getTitle());
            prRep.setCreator(reportDto.getCreator());
            prRep.setBoardId(reportDto.getBoardId());
            prRep.setBoardName(reportDto.getBoardName());
            prRep.setCreatedDate(reportDto.getCreatedDate());
            prRep.setUpdatedDate(reportDto.getUpdatedDate());
            prRep.setClosedDate(reportDto.getClosedDate());
            prRep.setTypeId(reportDto.getTypeId());
            prRep.setClosed(reportDto.isClosed());
            prRep.setAdmins(reportDto.getAdmins());

            prRep.setTargetPoints(0);
            prRep.setTargetHours(0L);
            prRep.setTargetQatDefectHours(0L);
            prRep.setTargetQatDefectMin(0);
            prRep.setTargetQatDefectMax(0);
            prRep.setTargetUatDefectHours(0L);
            prRep.setTargetUatDefectMin(0);
            prRep.setTargetUatDefectMax(0);

            prRep.setActualHours(0L);
            prRep.setActualPoints(0);
            prRep.setActualQatDefectHours(0L);
            prRep.setActualQatDefectPoints(0);
            prRep.setActualUatDefectHours(0L);
            prRep.setActualUatDefectPoints(0);

            Chart chart = new Chart();
            chart.setLabel(new String[]{"0", "1", "2"});
            chart.setActual(new int[]{3, 2, 1});
            chart.setTarget(new int[]{3, 3, 0});

            prRep.setChart(chart);

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        return prRep;
    }

    private Chart getChatData(List<IssuesByDayDto> issuesByDayList, float targetPoint, float actualPoint, int velocity) {
        Chart chart = new Chart();

        HelperMethods helperMethods = new HelperMethods();

        String date = "0,";
        for (IssuesByDayDto issuesByDayDto : issuesByDayList) {
            date += issuesByDayDto.getDate() + ",";
        }

        String[] dateArray = date.split(",");
        chart.setLabel(dateArray);
        int[] actual = new int[dateArray.length];
        int[] target = new int[dateArray.length];

        actual[0] = (int) targetPoint;
        target[0] = (int) targetPoint;
        List<Integer> ii = new ArrayList<>();
        ii.add(actual[0]);

        for (int i = 1; i < dateArray.length; i++) {

            if (helperMethods.isCurrentDay(dateArray[i])) {
                for (IssuesByDayDto issuesDto : issuesByDayList) {
                    if (issuesDto.getDate().equals(dateArray[i])) {
                        for (SprintIssueDto sprintIssueDto : issuesDto.getIssues()) {
                            actual[i] = actual[i - 1] - sprintIssueDto.getPoint();
                            ii.add(actual[i]);
                        }
                    }
                }
            }

            target[i] = target[i - 1] - velocity;
        }

        int[] array = new int[ii.size()];
        for (int i = 0; i < ii.size(); i++) array[i] = ii.get(i);

        chart.setActual(array);
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
