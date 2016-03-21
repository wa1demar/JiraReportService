package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDtoBuilder;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.*;
import com.swansoftwaresolutions.jirareport.domain.entity.builder.ReportBuilder;
import com.swansoftwaresolutions.jirareport.domain.repository.*;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        for(JiraSprint jiraSprint : jiraSprints) {
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
        ReportDto rDto =  reportMapper.toDto(reportRepository.findById(id));
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

        for(Sprint target : targetSprints) {
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
}
