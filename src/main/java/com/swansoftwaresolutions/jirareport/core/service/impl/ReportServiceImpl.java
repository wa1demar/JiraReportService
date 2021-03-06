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
import com.swansoftwaresolutions.jirareport.domain.model.Paged;
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
    JiraProjectRepository jiraProjectRepository;

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

        List<Long> ids = reportDtos.stream().map(r -> r.getBoardId()).collect(Collectors.toList());
        Map<Long, JiraBoard> boards = jiraBoardRepository.findByIds(ids).stream().collect(Collectors.toMap(JiraBoard::getId, b -> b));

        for (ReportDto dto : reportDtos) {
            if (dto.getBoardId() != null) {
                JiraBoard board = boards.get(dto.getBoardId());
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

        if (newReportDto.getTypeId() != 1) {
            newReport.setBoardId(null);
        }

        Report addedReport = reportRepository.add(newReport);

        if (addedReport.getTypeId() == 1) {

            List<JiraSprint> jiraSprints = jiraSprintRepository.findByBoardId(addedReport.getBoardId());

            List<Sprint> sprints = new ArrayList<>();
            for (JiraSprint jiraSprint : jiraSprints) {
                Sprint sprint = new Sprint();
                sprint.setJiraSprint(jiraSprint);
                sprint.setReport(addedReport);

                sprints.add(sprint);
            }

            sprintRepository.addAll(sprints);
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

        List<Long> ids = reportDtos.stream().map(r -> r.getBoardId()).collect(Collectors.toList());
        Map<Long, JiraBoard> boards = jiraBoardRepository.findByIds(ids).stream().collect(Collectors.toMap(JiraBoard::getId, b -> b));

        for (ReportDto dto : reportDtos) {
            if (dto.getBoardId() != null) {
                JiraBoard board = boards.get(dto.getBoardId());
                dto.setBoardName(board.getName());
                dto.setJiraBoardId(board.getBoardId());
            }
        }

        return new ReportListDtoBuilder().reportsDto(reportDtos).build();
    }

    @Override
    public ReportListDto retrieveAllClosedReportsListPaginated(int page) {
        Paged paged = reportRepository.findAllClosedPaginated(page);
        List<ReportDto> reportDtos = reportMapper.toDtos(paged.getList());

        List<Long> ids = reportDtos.stream().map(r -> r.getBoardId()).collect(Collectors.toList());
        Map<Long, JiraBoard> boards = jiraBoardRepository.findByIds(ids).stream().collect(Collectors.toMap(JiraBoard::getId, b -> b));

        for (ReportDto dto : reportDtos) {
            if (dto.getBoardId() != null) {
                JiraBoard board = boards.get(dto.getBoardId());
                dto.setBoardName(board.getName());
                dto.setJiraBoardId(board.getBoardId());
            }
        }


        return new ReportListDtoBuilder()
                .page(page)
                .pages((int) Math.floor(paged.getTotal() / 10) + 1)
                .items(paged.getTotal())
                .reportsDto(reportDtos)
                .build();
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

            List<SprintDeveloper> developers = sprintDeveloperRepository.findBySprintId(target.getId()); // TODO : push from loop

            Sprint newSprint = sprintRepository.add(sprint);

            for (SprintDeveloper developer : developers) {
                developer.setId(null);
                developer.setSprint(newSprint);

                sprintDeveloperRepository.add(developer); // TODO: move to repository?
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

        List<Long> ids = reportDtos.stream().map(r -> r.getBoardId()).collect(Collectors.toList());
        Map<Long, JiraBoard> boards = jiraBoardRepository.findByIds(ids).stream().collect(Collectors.toMap(JiraBoard::getId, b -> b));
        for (ReportDto dto : reportDtos) {
            if (dto.getBoardId() != null) {
                JiraBoard board = boards.get(dto.getBoardId());
                dto.setBoardName(board.getName());
                dto.setJiraBoardId(board.getBoardId());
            }
        }

        return new ReportListDtoBuilder().reportsDto(reportDtos).build();
    }

    @Override
    public ReportListDto retrieveAllOngoingReportsListPaginated(int page) {
        Paged paged = reportRepository.findAllOpenedPaginated(page);
        List<ReportDto> reportDtos = reportMapper.toDtos(paged.getList());

        List<Long> ids = reportDtos.stream().map(r -> r.getBoardId()).collect(Collectors.toList());
        Map<Long, JiraBoard> boards = jiraBoardRepository.findByIds(ids).stream().collect(Collectors.toMap(JiraBoard::getId, b -> b));

        for (ReportDto dto : reportDtos) {
            if (dto.getBoardId() != null) {
                JiraBoard board = boards.get(dto.getBoardId());
                dto.setBoardName(board.getName());
                dto.setJiraBoardId(board.getBoardId());
            }
        }


        return new ReportListDtoBuilder()
                .page(page)
                .pages((int) Math.floor(paged.getTotal() / 10) + 1)
                .items(paged.getTotal())
                .reportsDto(reportDtos)
                .build();
    }

    @Override
    public ProjectDashboardDto findProjectDashboard(Long reportId) {
        ProjectDashboardDto projectDashboardDto = new ProjectDashboardDto();

        try {
            Report report = reportRepository.findById(reportId);
            if (report.getTypeId() == 3) {
                projectDashboardDto.setReport(buildSpecialReport(report));
                projectDashboardDto.setSprints(buildSpecialSprints(report));
            } else if (report.getBoardId() == null) {
                projectDashboardDto.setSprints(buildManualSprints(report));
                projectDashboardDto.setReport(buildProjectReport(reportId, projectDashboardDto.getSprints()));
            } else {
                projectDashboardDto.setSprints(buildAutomationSprints(report));
                projectDashboardDto.setReport(buildAutomaticProjectReport(report, projectDashboardDto.getSprints()));
            }

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
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

    private List<SprintProjectReportDto> buildSpecialSprints(Report report) throws NoSuchEntityException {
        List<SprintProjectReportDto> sprints = new ArrayList<>();

        List<FullSprintDto> sprintDtoList = sprintService.findByReport(report);

        for (FullSprintDto sprintDto : sprintDtoList) {
            SprintProjectReportDto sprint = new SprintProjectReportDto();
            sprint.setId(sprintDto.getId());
            sprint.setName(sprintDto.getName());
            sprint.setNotCountTarget(sprintDto.isNotCountTarget());
            sprint.setShowUat(sprintDto.isShowUat());
            sprint.setShowOutOfRange(sprintDto.isShowOutOfRange());
            sprint.setState(sprintDto.getState());
            sprint.setType(sprintDto.getType());
            sprint.setStartDate(sprintDto.getStartDate());
            sprint.setEndDate(sprintDto.getEndDate());
            sprint.setCompleteDate(sprintDto.getEndDate());
            sprint.setDescription(sprintDto.getDescription());
            sprint.setIssues(sprintDto.getIssues());

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

            sprints.add(sprint);
        }

        Collections.sort(sprints, (o1, o2) -> o2.getStartDate().compareTo(o1.getStartDate()));

        new Thread(() -> {
            projectTotalRepository.saveOrUpdate(new CacheProjectTotalBuilder()
                    .vTargetPoints(0)
                    .vActualPoints(0)
                    .qtargetMin(0)
                    .qtargetMax(0)
                    .qActualPoints(0)
                    .qTargetHours(0L)
                    .qActualHours(0L)
                    .utargetMin(0)
                    .utargetMax(0)
                    .uActualPoints(0)
                    .uTargetHours(0L)
                    .report(report)
                    .vActualHours(0L)
                    .vTargetHours(0L)
                    .issues(sprints.size() > 0 ? sprints.get(0).getIssues() : "")
                    .description(sprints.size() > 0 ? sprints.get(0).getDescription() : "")
                    .startDate(sprints.size() > 0 ? sprints.get(0).getStartDate() : null)
                    .endDate(sprints.size() > 0 ? sprints.get(0).getEndDate() : null)
                    .build());
        }).start();

        return sprints;
    }

    private List<SprintProjectReportDto> buildManualSprints(Report report) {

        HelperMethods help = new HelperMethods();

        List<SprintProjectReportDto> sprints = new ArrayList<>();

        List<FullSprintDto> sprintDtoList;
        try {
            sprintDtoList = sprintService.findByReport(report);

            for (FullSprintDto sprintDto : sprintDtoList) {

                List<IssuesByDayDto> issuesByDayList = getIssueByDay(sprintDto);

                SprintProjectReportDto sprint = new SprintProjectReportDto();
                sprint.setId(sprintDto.getId());
                sprint.setName(sprintDto.getName());
                sprint.setNotCountTarget(sprintDto.isNotCountTarget());
                sprint.setShowUat(sprintDto.isShowUat());
                sprint.setShowOutOfRange(sprintDto.isShowOutOfRange());
                sprint.setState(sprintDto.getState());
                sprint.setType(sprintDto.getType());
                sprint.setStartDate(sprintDto.getStartDate());
                sprint.setEndDate(sprintDto.getEndDate());
                sprint.setCompleteDate(sprintDto.getEndDate());

                List<SprintDeveloperDto> sprintDevList = sprintDto.getDevelopers();

                if (sprintDevList != null) {
                    List<SprintDeveloperDto> sprintDevelopers = new ArrayList<>();
                    for (SprintDeveloperDto dev : sprintDevList) {

                        for (IssuesByDayDto issuesByDayDto : issuesByDayList) {
                            for (SprintIssueDto issue : issuesByDayDto.getIssues()) {
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
            sprintDtoList = sprintService.findByReport(report);

            List<Long> ids = sprintDtoList.stream().map(r -> r.getJiraSprintId()).collect(Collectors.toList());
            List<JiraIssueDto> issues = jiraIssueService.findBySprintIds(ids);
            Map<Long, List<JiraIssueDto>> map = createMap(issues);

            for (FullSprintDto sprintDto : sprintDtoList) {
                if (sprintDto.getState() == null || sprintDto.getState().equalsIgnoreCase("future") || sprintDto.getDevelopers() == null || sprintDto.getDevelopers().size() == 0) {
                    continue;
                }

                List<JiraIssueDto> jiraIssueList = map.get(sprintDto.getJiraSprintId());

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
                sprint.setShowOutOfRange(sprintDto.isShowOutOfRange());
                sprint.setCompleteDate(sprintDto.getCompleteDate());

                if (sprintDto != null) {
                    List<SprintDeveloperDto> sprintDevelopers = new ArrayList<>();
                    for (SprintDeveloperDto dev : sprintDto.getDevelopers()) {
                        if (jiraIssueList == null) continue;

                        for (JiraIssueDto issue : jiraIssueList) {
                            if (dev.getDeveloperLogin().equals(issue.getAssignedKey())) {

                                if (issue.getStatusName() != null && (isAgileDone(issue.getStatusName(), agileDoneNames))) {
                                    if (issue.getIssueTypeName().equalsIgnoreCase("Story")) {
                                        issuesSet.add(issue);
                                        dev.setActualPoints(dev.getActualPoints() + (int) issue.getPoints());
                                        dev.setActualHours(help.isNull(dev.getActualHours()) + issue.getTimeSpent());
                                    } else if (issue.getIssueTypeName().equalsIgnoreCase("Bug")) {
                                        if (isQAT(dev, report, issue)) {
                                            dev.setDefectActual(dev.getDefectActual() + 1);
                                            dev.setDefectActualHours(help.isNull(dev.getDefectActualHours()) + issue.getTimeSpent());
                                            dev.setActualHours(help.isNull(dev.getActualHours()) + issue.getTimeSpent());
                                        } else {
                                            dev.setUatDefectActualHours(help.isNull(dev.getUatDefectActualHours()) + issue.getTimeSpent());
                                            dev.setUatDefectActual(help.isNull(dev.getUatDefectActual()) + 1);
                                            dev.setActualHours(help.isNull(dev.getActualHours()) + issue.getTimeSpent());
                                        }
                                    }
                                    dev.setUatDefectHours(help.isNull(dev.getUatDefectHours()));
                                }
                            }
                        }

                        dev.setTargetHours(Math.round(dev.getParticipationLevel() * dev.getDaysInSprint()) * 8);

                        dev.setDefectTargetHours((long) Math.round(help.isNull(dev.getDefectHours())));
                        dev.setDefectActualHours((long) Math.round(help.isNull(dev.getDefectActualHours()) / 3600));

                        dev.setUatDefectTargetHours((long) Math.round(help.isNull(dev.getUatDefectTargetHours())));
                        dev.setUatDefectActualHours((long) Math.round(help.isNull(dev.getUatDefectActualHours()) / 3600));
                        dev.setUatDefectActual(help.isNull(dev.getUatDefectActual()));
                        dev.setUatDefectHours((long) Math.round(help.isNull(dev.getUatDefectHours())));
                        dev.setActualHours((long) Math.round(help.isNull(dev.getActualHours()) / 3600));

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

                sprint.setChart(getChatData(issuesSet, sprintDto, sprint.getTargetPoints()));
                sprints.add(sprint);
            }

        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        sprints.sort((o1, o2) -> o1.getStartDate().compareTo(o2.getStartDate()));

        return sprints;
    }

    private Map<Long, List<JiraIssueDto>> createMap(List<JiraIssueDto> issues) {
        Map<Long, List<JiraIssueDto>> result = new HashMap<>();

        for (JiraIssueDto i : issues) {
            if (result.get(i.getSprintId()) == null) {
                List<JiraIssueDto> devs = new ArrayList<>();
                devs.add(i);

                result.put(i.getSprintId(), devs);
            } else {
                List<JiraIssueDto> devs = result.get(i.getSprintId());
                devs.add(i);
                result.put(i.getSprintId(), devs);
            }
        }

        return result;
    }


    private boolean isAgileDone(String statusName, List<String> agileDoneNames) {
        for (String status : agileDoneNames) {
            if (status.equalsIgnoreCase(statusName)) {
                return true;
            }
        }
        return false;

    }

    private boolean isQAT(SprintDeveloperDto dev, Report report, JiraIssueDto issu) {
        for (JiraUser jiraUser : report.getAdmins()) {
            if (!jiraUser.getLogin().equals(issu.getCreatorName())) {
                return true;
            }
        }
        return false;
    }

    private ProjectReportDto buildSpecialReport(Report report) {
        ProjectReportDto prRep = new ProjectReportDto();
        ReportDto reportDto = reportMapper.toDto(report);
        prRep.setId(reportDto.getId());
        prRep.setTitle(reportDto.getTitle());
        prRep.setCreator(reportDto.getCreator());
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

        prRep.setChart(null);

        return prRep;

    }

    private ProjectReportDto buildAutomaticProjectReport(Report report, List<SprintProjectReportDto> sprints) {
        ProjectReportDto prRep = new ProjectReportDto();

        List<SprintProjectReportDto> targetSprints = new ArrayList<>();

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
                if (sprint.getState() == null || sprint.getState().equalsIgnoreCase("future") || sprint.getSprintTeam() == null || sprint.getSprintTeam().size() == 0 || !sprint.getState().equalsIgnoreCase("closed")) {
                    continue;
                }

                targetSprints.add(sprint);

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

        prRep.setChart(helpM.generateReportChart(targetSprints));

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
                    .issues(sprints != null && sprints.size() > 0 ? sprints.get(0).getIssues() : "")
                    .description(sprints != null && sprints.size() > 0 ? sprints.get(0).getDescription() : "")
                    .startDate(sprints.size() > 0 ? sprints.get(0).getStartDate() : null)
                    .endDate(sprints.size() > 0 ? sprints.get(0).getEndDate() : null)
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
                    if (!sprint.isNotCountTarget() && sprint.getState() != null && (sprint.getState().equalsIgnoreCase("closed"))) {
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

            prRep.setChart(helpM.generateReportChart(sprints));

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
                        .issues(sprints != null && sprints.size() > 0 ? sprints.get(0).getIssues() : "")
                        .description(sprints != null && sprints.size() > 0 ? sprints.get(0).getDescription() : "").startDate(sprints.size() > 0 ? sprints.get(0).getStartDate() : null)
                        .endDate(sprints.size() > 0 ? sprints.get(0).getEndDate() : null)
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
                if (dateString.isEmpty()) continue;
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
                Date date2;
                try {
                    date2 = sdf.parse(sprintIssueDto.getIssueDate());
                    if (helperMethods.isSameDate(currentDate, date2)) {
                        issues.add(sprintIssueDto);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
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

        Chart chart;

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

        String endDateStr = formatter.format(sprintDto.getEndDate());

        while (endDate.after(startDate) || help.isSameDate(startDate.getTime(), endDate.getTime())) {
            Date currentDate = startDate.getTime();

            if (help.isWeekend(currentDate) || nonWorkingDays.contains(currentDate)) {
                startDate.add(Calendar.DATE, 1);
                continue;
            }

            date = date + "," + formatter.format(currentDate);

            float tar = ii.get(ii.size() - 1);
            Iterator<JiraIssueDto> iterator = issues.iterator();
            while (iterator.hasNext()) {
                JiraIssueDto element = iterator.next();

                if (help.isSameDate(currentDate, element.getUpdated())) {
                    tar = tar - element.getPoints();
                    iterator.remove();
                }
            }

            if (currentDate.before(new Date())) {
                ii.add((int) tar);
            }
            startDate.add(Calendar.DATE, 1);
        }

        chart = configureChart(date, endDateStr, ii, targetPoints);

        if (sprintDto.isShowOutOfRange()) {
            Calendar addDate = endDate;
            if (issues.size() > 0) {
                endDate = getFinishDate(issues, endDate);

                while (!addDate.after(endDate)) {
                    Date currentDate = addDate.getTime();

                    if (help.isWeekend(currentDate) || nonWorkingDays.contains(currentDate)) {
                        addDate.add(Calendar.DATE, 1);
                        continue;
                    }

                    date = date + "," + formatter.format(currentDate);

                    float tar = ii.get(ii.size() - 1);
                    Iterator<JiraIssueDto> iterator = issues.iterator();
                    while (iterator.hasNext()) {
                        JiraIssueDto element = iterator.next();

                        if (help.isSameDate(currentDate, element.getUpdated())) {
                            tar = tar - element.getPoints();
                            iterator.remove();
                        }
                    }

                    if (currentDate.before(new Date())) {
                        ii.add((int) tar);
                    }

                    addDate.add(Calendar.DATE, 1);
                }
            }

            if (issues.size() > 0) {
                float tar = ii.get(ii.size() - 1);
                for (JiraIssueDto issueDto : issues) {
                    tar = tar - issueDto.getPoints();
                }
                ii.set(ii.size() - 1, (int) tar);
            }

            chart = configureChart(chart, date, ii);
        }

        return chart;
    }

    private Chart configureChart(Chart chart, String date, List<Integer> ii) {
        String[] dateArrayNew = date.split(",");
        chart.setLabel(dateArrayNew);

        int[] arrayInts = new int[ii.size()];
        for (int i = 0; i < ii.size(); i++) arrayInts[i] = ii.get(i);

        double[] arrayTar = new double[dateArrayNew.length];
        double[] arrayTarOld = chart.getTarget();
        for (int i = 0; i < dateArrayNew.length; i++) {
            if (chart.getTarget().length > i) {
                arrayTar[i] = arrayTarOld[i];
            } else {
                break;
            }
        }

        chart.setTarget(arrayTar);
        chart.setActual(arrayInts);

        return chart;
    }

    private Chart configureChart(String date, List<Integer> ii, float targetPoints) {
        Chart chart = new Chart();

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

    private Chart configureChart(String date, String closeDate, List<Integer> ii, float targetPoints) {
        Chart chart = new Chart();

        String[] dateArray = date.split(",");
        chart.setLabel(dateArray);

        int[] array = new int[ii.size()];
        for (int i = 0; i < ii.size(); i++) array[i] = ii.get(i);

        chart.setActual(array);

        double[] targetArray = new double[dateArray.length];

        targetArray[0] = targetPoints;

        int index = 0;
        for (int i = 0; i<dateArray.length; i++){
            if (dateArray[i].equals(closeDate)){
                index = i;
            }
        }

        for (int i = 1; i < dateArray.length; i++) {
            if (i<index) {
                targetArray[i] = (targetPoints - targetPoints / (index) * i);
            } else {
                targetArray[i]=0;
            }
        }

        chart.setTarget(targetArray);

        return chart;
    }

    private Calendar getFinishDate(Set<JiraIssueDto> issues, Calendar endDate) {
        Calendar lastDate = endDate;

        for (JiraIssueDto issue : issues) {
            Calendar date = Calendar.getInstance();
            date.setTime(issue.getUpdated());
            if (date.compareTo(lastDate) > 0) {
                lastDate = date;
            }
        }
        return lastDate;
    }
}
