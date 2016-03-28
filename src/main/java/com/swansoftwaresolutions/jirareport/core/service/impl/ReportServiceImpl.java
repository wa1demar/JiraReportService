package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.dashboard.*;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_issue.IssuesByDayDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_issue.SprintIssueListDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDtoBuilder;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.FullSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.core.service.*;
import com.swansoftwaresolutions.jirareport.domain.entity.*;
import com.swansoftwaresolutions.jirareport.domain.entity.builder.CacheProjectTotalBuilder;
import com.swansoftwaresolutions.jirareport.domain.entity.builder.ReportBuilder;
import com.swansoftwaresolutions.jirareport.domain.repository.*;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.helper.HelperMethods;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraIssueDto;
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

    @Autowired
    JiraBoardService jiraBoardService;

    @Autowired
    JiraSprintsService jiraSprintsService;

    @Autowired
    ConfigService configService;
    @Autowired
    JiraIssueService jiraIssueService;


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
            sprint.setName(target.getName());
            sprint.setJiraSprint(target.getJiraSprint());
            sprint.setReport(addedReport);
            sprint.setNotCountTarget(target.isNotCountTarget());
            sprint.setShowUAT(target.isShowUAT());
            sprint.setType(target.getType());
            sprint.setStartDate(target.getStartDate());
            sprint.setEndDate(target.getEndDate());
            sprint.setState(target.getState());

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
        projectTotalRepository.deleteByReportId(id);
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

        try {
            Report report = reportRepository.findById(reportId);
            if (report.getBoardId() == null) {
                projectDashboardDto.setSprints(buildManualSprints(report.getId()));
                projectDashboardDto.setReport(buildProjectReport(reportId, projectDashboardDto.getSprints()));
            } else {
                projectDashboardDto.setSprints(buildAutomationSprints(report));
                projectDashboardDto.setReport(buildAutomaticProjectReport(report, projectDashboardDto.getSprints()));
            }

        } catch (NoSuchEntityException e) {

        }

        return projectDashboardDto;
    }

    @Override
    public long getClosedSprintCount(Long reportId) {
        return reportRepository.closedSprintCount(reportId);
    }

    @Override
    public long getSprintCount(Long reportId) {
        return reportRepository.sprintCount(reportId);
    }

    @Override
    public boolean showUat(Long reportId) {
        return reportRepository.showUat(reportId);
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
                    for (SprintDeveloperDto dev : sprintDevList) {
                        Set<SprintIssueDto> issuesSet = new HashSet<>();
                        for (IssuesByDayDto issuesByDayDto : issuesByDayList) {
                            for (SprintIssueDto issue : issuesByDayDto.getIssues()) {
                                issuesSet.add(issue);
                            }
                        }

                        for (SprintIssueDto issue : issuesSet) {
                            if (dev.getDeveloperLogin().equals(issue.getAssignee())) {
                                if (issue.getStatusName().equals("Done")) {
                                    if (issue.getTypeName().equals("Story")) {
                                        dev.setActualPoints(dev.getActualPoints() + issue.getPoint());
                                        dev.setActualHours(help.isNull(dev.getActualHours()) + (long) issue.getHours());
                                    } else if (issue.getTypeName().equals("QAT Defect")) {
                                        dev.setDefectActual(dev.getDefectActual() + 1);
                                        dev.setDefectActualHours(help.isNull(dev.getDefectActualHours()) + (long) issue.getHours());
                                        dev.setActualHours(help.isNull(dev.getActualHours()) + (long) issue.getHours());
                                    } else if (issue.getTypeName().equals("UAT Defect")) {
                                        dev.setUatDefectHours(help.isNull(dev.getDefectHours()) + (long) issue.getHours());
                                        dev.setUatDefectActualHours(help.isNull(dev.getUatDefectActualHours()) + (long) issue.getHours());
                                        dev.setUatDefectActual(help.isNull(dev.getUatDefectActual()) + 1);
                                        dev.setActualHours(help.isNull(dev.getActualHours()) + (long) issue.getHours());
                                    }
                                }
                            }
                        }

                        dev.setTargetHours(Math.round(dev.getParticipationLevel() * dev.getDaysInSprint()) * 8);

                        dev.setDefectTargetHours(help.isNull(dev.getDefectHours().longValue()));
                        dev.setDefectActualHours(help.isNull(dev.getDefectActualHours()));

                        dev.setUatDefectTargetHours(help.isNull(dev.getUatDefectHours().longValue()));
                        dev.setUatDefectActualHours(help.isNull(dev.getUatDefectActualHours()));
                        dev.setUatDefectActual(help.isNull(dev.getUatDefectActual()));

                        sprintDevelopers.add(dev);
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
                        sprint.setTargetHours(help.isNull(sprint.getTargetHours()) + help.isNull(dev.getTargetHours()));
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

                sprint.setChart(help.getChatData(issuesByDayList, sprint.getTargetPoints()));

                sprints.add(sprint);
            }
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        return sprints;
    }

    private List<SprintProjectReportDto> buildAutomationSprints(Report report) {

        String agileDoneName = configService.retrieveConfig().getAgileDoneName();
        List<String> agileDoneNames = new ArrayList<>();
        for (String dateString : Arrays.asList(agileDoneName.split(","))) {
            agileDoneNames.add(dateString);
        }

        HelperMethods help = new HelperMethods();

        List<SprintProjectReportDto> sprints = new ArrayList<>();

        List<FullSprintDto> sprintDtoList;

        try {
            sprintDtoList = sprintService.findByReportId(report.getId());

            for (FullSprintDto sprintDto : sprintDtoList) {
                if (sprintDto.getState() == null || sprintDto.getState().equalsIgnoreCase("future") || sprintDto.getDevelopers() == null || sprintDto.getDevelopers().size() == 0) {
                    continue;
                }

                List<JiraIssueDto> jiraIssueList = new ArrayList<>();
                jiraIssueList = jiraIssueService.findBySprintId(sprintDto.getJiraSprintId());

                Set<JiraIssueDto> issuesSet = new HashSet<>();

                SprintProjectReportDto sprint = new SprintProjectReportDto();
                sprint.setId(sprintDto.getId());
                sprint.setName(sprintDto.getName());
                sprint.setState(sprintDto.getState());
                sprint.setType(sprintDto.getType());
                sprint.setStartDate(sprintDto.getStartDate());
                sprint.setEndDate(sprintDto.getEndDate());
                sprint.setReportId(sprintDto.getReportId());
                sprint.setShowUat(sprintDto.isShowUat());
                sprint.setNotCountTarget(sprintDto.isNotCountTarget());
                sprint.setClosed(sprintDto.getState().equalsIgnoreCase("closed"));
                sprint.setCompleteDate(sprintDto.getEndDate());
                sprint.setSprintTeam(sprintDto.getSprintTeams());

                if (sprintDto != null) {
                    List<SprintDeveloperDto> sprintDevelopers = new ArrayList<>();
                    for (SprintDeveloperDto dev : sprintDto.getDevelopers()) {
                        for (JiraIssueDto issue : jiraIssueList) {
                            if (dev.getDeveloperLogin().equals(issue.getAssignedKey())) {

                                if (issue.getStatusName() != null && (isAgileDone(issue.getStatusName(), agileDoneNames))) {
                                    if (issue.getIssueTypeName().equalsIgnoreCase("Story")) {
                                        issuesSet.add(issue);
                                        dev.setActualPoints(dev.getActualPoints() + (int) issue.getPoints());
                                        dev.setActualHours(help.isNull(dev.getActualHours()) + Math.round(issue.getTimeSpent() / 60));
                                    } else if (issue.getIssueTypeName().equalsIgnoreCase("Bug")) {
                                        if (isQAT(dev, report)) {
                                            dev.setDefectActual(dev.getDefectActual() + 1);
                                            dev.setDefectActualHours(help.isNull(dev.getDefectActualHours()) + Math.round(issue.getTimeSpent() / 60));
                                            dev.setActualHours(help.isNull(dev.getActualHours()) + Math.round(issue.getTimeSpent() / 60));
                                        } else {
                                            dev.setUatDefectHours(help.isNull(dev.getDefectHours()) + Math.round(issue.getTimeSpent() / 60));
                                            dev.setUatDefectActualHours(help.isNull(dev.getUatDefectActualHours()) + Math.round(issue.getTimeSpent() / 60));
                                            dev.setUatDefectActual(help.isNull(dev.getUatDefectActual()) + 1);
                                            dev.setActualHours(help.isNull(dev.getActualHours()) + Math.round(issue.getTimeSpent() / 60));
                                        }
                                    }
                                }
                            }
                        }

                        dev.setTargetHours(Math.round(dev.getParticipationLevel() * dev.getDaysInSprint()) * 8);

                        dev.setDefectTargetHours(help.isNull(dev.getDefectHours().longValue()));
                        dev.setDefectActualHours(help.isNull(dev.getDefectActualHours()));

                        dev.setUatDefectTargetHours(help.isNull(dev.getUatDefectHours().longValue()));
                        dev.setUatDefectActualHours(help.isNull(dev.getUatDefectActualHours()));
                        dev.setUatDefectActual(help.isNull(dev.getUatDefectActual()));

                        sprintDevelopers.add(dev);
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
                        sprint.setActualHours(help.isNull(sprint.getActualHours()) + Math.round(help.isNull(dev.getActualHours()) / 60));
                        sprint.setTargetHours(help.isNull(sprint.getTargetHours()) + help.isNull(dev.getTargetHours()));
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

                sprint.setChart(getChatData(issuesSet, sprintDto, sprint.getTargetPoints()));
                sprints.add(sprint);
            }

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        sprints.sort(new Comparator<SprintProjectReportDto>() {
            @Override
            public int compare(SprintProjectReportDto o1, SprintProjectReportDto o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });

        return sprints;
    }

    private boolean isAgileDone(String statusName, List<String> agileDoneNames) {
        for (String status :agileDoneNames){
            if (status.equalsIgnoreCase(statusName)) {
                return true;
            }
        }
        return false;

    }

    private boolean isQAT(SprintDeveloperDto dev, Report report) {
        for (JiraUser jiraUser : report.getAdmins()) {
            if (jiraUser.getLogin().equals(dev.getDeveloperLogin()))
                return true;
        }
        return false;
    }

    private ProjectReportDto buildAutomaticProjectReport(Report report, List<SprintProjectReportDto> sprints) {
        ProjectReportDto prRep = new ProjectReportDto();

        HelperMethods helpM = new HelperMethods();

        ReportDto reportDto = reportMapper.toDto(report);
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
        prRep.setTargetHours(0L);
        prRep.setTargetQatDefectHours(0L);
        prRep.setTargetUatDefectHours(0L);

        boolean isShowUat = false;
        long closedCount = 0;

        if (sprints != null) {
            for (SprintProjectReportDto sprint : sprints) {
                if (sprint.getState() == null || sprint.getState().equalsIgnoreCase("future") || sprint.getSprintTeam() == null || sprint.getSprintTeam().size() == 0) {
                    continue;
                }

                if (sprint.isShowUat()) {
                    isShowUat = true;
                }
                if (!sprint.isNotCountTarget() && sprint.getState() != null && sprint.getState().equalsIgnoreCase("Closed")) {
                    prRep.setTargetPoints(helpM.isNullFloat(prRep.getTargetPoints()) + helpM.isNullFloat(sprint.getTargetPoints()));
                    prRep.setTargetHours(helpM.isNull(prRep.getTargetHours()) + helpM.isNull(sprint.getTargetHours()));
                    prRep.setTargetQatDefectHours(helpM.isNull(prRep.getTargetQatDefectHours()) + helpM.isNull(sprint.getTargetQatDefectHours()));
                    prRep.setTargetQatDefectMin(prRep.getTargetQatDefectMin() + sprint.getTargetQatDefectMin());
                    prRep.setTargetQatDefectMax(prRep.getTargetQatDefectMax() + sprint.getTargetQatDefectMax());
                    prRep.setTargetUatDefectHours(helpM.isNull(prRep.getTargetUatDefectHours()) + helpM.isNull(sprint.getTargetUatDefectHours()));
                    prRep.setTargetUatDefectMin(prRep.getTargetUatDefectMin() + sprint.getTargetUatDefectMin());
                    prRep.setTargetUatDefectMax(prRep.getTargetUatDefectMax() + sprint.getTargetUatDefectMax());

                    prRep.setActualHours(helpM.isNull(prRep.getActualHours()) + Math.round(helpM.isNull(sprint.getActualHours()) / 60));
                    prRep.setActualPoints(helpM.isNullFloat(prRep.getActualPoints()) + helpM.isNullFloat(sprint.getActualPoints()));
                    prRep.setActualQatDefectHours(helpM.isNull(prRep.getActualQatDefectHours()) + helpM.isNull(sprint.getActualQatDefectHours()));
                    prRep.setActualQatDefectPoints(helpM.isNullFloat(prRep.getActualQatDefectPoints()) + helpM.isNullFloat(sprint.getActualQatDefectPoints()));
                    prRep.setActualUatDefectHours(helpM.isNull(prRep.getActualUatDefectHours()) + helpM.isNull(sprint.getActualUatDefectHours()));
                    prRep.setActualUatDefectPoints(helpM.isNullFloat(prRep.getActualUatDefectPoints()) + helpM.isNullFloat(sprint.getActualUatDefectPoints()));
                    closedCount++;
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

        prRep.setChart(helpM.generateReportChart(sprints, prRep.getTargetPoints()));

        final boolean finalIsShowUat = isShowUat;
        final long finalClosedCount = closedCount;
        new Thread(() -> {
            projectTotalRepository.saveOrUpdate(new CacheProjectTotalBuilder()
                    .vTargetPoints(prRep.getTargetPoints())
                    .vActualPoints(prRep.getActualPoints())
                    .qtargetMin(prRep.getTargetQatDefectMin())
                    .qtargetMax(prRep.getTargetQatDefectMax())
                    .qActualPoints(prRep.getActualQatDefectPoints())
                    .qTargetHours(prRep.getTargetQatDefectHours() != null ? prRep.getTargetQatDefectHours() : 0L)
                    .qActualHours(prRep.getActualQatDefectHours() != null ? prRep.getActualQatDefectHours() : 0L)
                    .utargetMin(prRep.getTargetUatDefectMin())
                    .utargetMax(prRep.getTargetUatDefectMax())
                    .uActualPoints(prRep.getActualUatDefectPoints())
                    .uTargetHours(prRep.getTargetUatDefectHours() != null ? prRep.getTargetUatDefectHours() : 0L)
                    .uActualHours(prRep.getActualUatDefectHours() != null ? prRep.getActualUatDefectHours() : 0L)
                    .chartActual(Arrays.stream(prRep.getChart().getActual()).boxed().toArray(Integer[]::new))
                    .chartTarget(Arrays.stream(prRep.getChart().getTarget()).boxed().toArray(Double[]::new))
                    .chartLabels(prRep.getChart().getLabel())
                    .report(reportMapper.fromDto(reportDto))
                    .vActualHours(prRep.getActualHours() != null ? prRep.getActualHours() : 0L)
                    .vTargetHours(prRep.getTargetHours() != null ? prRep.getTargetHours() : 0L)
                    .showUat(finalIsShowUat)
                    .sprintsCount(sprints != null ? sprints.size() : 0)
                    .closedSprintsCount(finalClosedCount)
                    .build());
        }).start();


        return prRep;
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
            prRep.setTargetHours(0L);
            prRep.setTargetQatDefectHours(0L);
            prRep.setTargetUatDefectHours(0L);

            boolean isShowUat = false;
            long closedCount = 0;

            if (sprints != null) {
                for (SprintProjectReportDto sprint : sprints) {
                    if (sprint.isShowUat()) {
                        isShowUat = true;
                    }
                    if (!sprint.isNotCountTarget() && sprint.getState() != null && (sprint.getState().equals("Closed") || sprint.getState().equals("closed"))) {
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
                        closedCount++;
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

            prRep.setChart(helpM.generateReportChart(sprints, prRep.getTargetPoints()));

            final boolean finalIsShowUat = isShowUat;
            final long finalClosedCount = closedCount;
            new Thread(() -> {
                projectTotalRepository.saveOrUpdate(new CacheProjectTotalBuilder()
                        .vTargetPoints(prRep.getTargetPoints())
                        .vActualPoints(prRep.getActualPoints())
                        .qtargetMin(prRep.getTargetQatDefectMin())
                        .qtargetMax(prRep.getTargetQatDefectMax())
                        .qActualPoints(prRep.getActualQatDefectPoints())
                        .qTargetHours(prRep.getTargetQatDefectHours() != null ? prRep.getTargetQatDefectHours() : 0L)
                        .qActualHours(prRep.getActualQatDefectHours() != null ? prRep.getActualQatDefectHours() : 0L)
                        .utargetMin(prRep.getTargetUatDefectMin())
                        .utargetMax(prRep.getTargetUatDefectMax())
                        .uActualPoints(prRep.getActualUatDefectPoints())
                        .uTargetHours(prRep.getTargetUatDefectHours() != null ? prRep.getTargetUatDefectHours() : 0L)
                        .uActualHours(prRep.getActualUatDefectHours() != null ? prRep.getActualUatDefectHours() : 0L)
                        .chartActual(Arrays.stream(prRep.getChart().getActual()).boxed().toArray(Integer[]::new))
                        .chartTarget(Arrays.stream(prRep.getChart().getTarget()).boxed().toArray(Double[]::new))
                        .chartLabels(prRep.getChart().getLabel())
                        .report(reportMapper.fromDto(reportDto))
                        .vActualHours(prRep.getActualHours() != null ? prRep.getActualHours() : 0L)
                        .vTargetHours(prRep.getTargetHours() != null ? prRep.getTargetHours() : 0L)
                        .showUat(finalIsShowUat)
                        .sprintsCount(sprints != null ? sprints.size() : 0)
                        .closedSprintsCount(finalClosedCount)
                        .build());
            }).start();

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        return prRep;
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

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String nonWorkingDaysString = configService.retrieveConfig().getNonWorkingDays();
        List<Date> nonWorkingDays = new ArrayList<>();
        for (String dateString : Arrays.asList(nonWorkingDaysString.split(","))) {
            try {
                nonWorkingDays.add(formatter.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        while (!startDate.after(endDate)) {
            Date currentDate = startDate.getTime();

            List<SprintIssueDto> issues = new ArrayList<>();

            if (helperMethods.isWeekend(currentDate) || nonWorkingDays.contains(currentDate)) {
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

    public Chart getChatData(Set<JiraIssueDto> issues, FullSprintDto sprintDto, float targetPoints) {

        HelperMethods help = new HelperMethods();

        Chart chart = new Chart();

        String date = "0";

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(sprintDto.getStartDate());

        if (sprintDto.getEndDate() == null) {
            sprintDto.setEndDate(new Date());
        }
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(sprintDto.getEndDate());

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String nonWorkingDaysString = configService.retrieveConfig().getNonWorkingDays();
        List<Date> nonWorkingDays = new ArrayList<>();

        for (String dateString : Arrays.asList(nonWorkingDaysString.split(","))) {
            try {
                if (nonWorkingDays.size() > 0) {
                    nonWorkingDays.add(formatter.parse(dateString));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        List<Integer> ii = new ArrayList<>();
        ii.add((int) targetPoints);

        float target = targetPoints;

        while (!startDate.after(endDate)) {
            Date currentDate = startDate.getTime();

            if (help.isWeekend(currentDate) || nonWorkingDays.contains(currentDate)) {
                startDate.add(Calendar.DATE, 1);
                continue;
            }

            date = date + "," + formatter.format(currentDate);

            float tar = ii.get(ii.size()-1);
            Iterator<JiraIssueDto> iterator = issues.iterator();
            while (iterator.hasNext()) {
                JiraIssueDto element = iterator.next();

                if (help.isSameDate(currentDate, element.getUpdated())) {
                    tar = tar - element.getPoints();
                    iterator.remove();
                }
            }


            ii.add((int) tar);
            startDate.add(Calendar.DATE, 1);
        }

        String[] dateArray = date.split(",");
        chart.setLabel(dateArray);

        int[] array = new int[ii.size()];
        for (int i = 0; i < ii.size(); i++) array[i] = ii.get(i);

        chart.setActual(array);

        double[] targetArray = new double[dateArray.length];

        targetArray[0] = targetPoints;

        for (int i = 1; i < dateArray.length; i++) {
            targetArray[i] = (targetPoints - targetPoints / (dateArray.length - 1) * i);
        }

        chart.setTarget(targetArray);

        return chart;
    }
}
