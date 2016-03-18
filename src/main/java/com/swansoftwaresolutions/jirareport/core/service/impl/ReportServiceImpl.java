package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDtoBuilder;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.entity.builder.JiraUserBuilder;
import com.swansoftwaresolutions.jirareport.domain.entity.builder.ReportBuilder;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private JiraUserMapper jiraUserMapper;

    @Autowired
    private JiraUserRepository jiraUserRepository;

    @Override
    public ReportListDto retrieveAllReportsList() {
        List<ReportDto> reportDtos = reportMapper.toDtos(reportRepository.findAll());

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

        return reportMapper.toDto(addedReport);
    }

    @Override
    public ReportDto retrieveReportByID(long id) throws NoSuchEntityException {
        return reportMapper.toDto(reportRepository.findById(id));
    }

    @Override
    public ReportListDto retrieveAllClosedReportsList() {
        List<ReportDto> reportDtos = reportMapper.toDtos(reportRepository.findAllClosed());

        return new ReportListDtoBuilder().reportsDto(reportDtos).build();
    }

    @Override
    public ReportDto copy(long id) throws NoSuchEntityException {

        Report report = reportRepository.findById(id);

        Report newReport = new ReportBuilder()
                .title("Copy of " + report.getTitle())
                .creator(report.getCreator())
                .boardId(report.getBoardId())
                .isClosed(report.getClosed())
                .admins(report.getAdmins())
                .closedDate(report.getClosedDate())
                .createdDate(report.getCreatedDate())
                .syncDate(report.getSyncDate())
                .typeId(report.getTypeId())
                .build();

        return reportMapper.toDto(reportRepository.add(newReport));
    }

    @Override
    public ReportDto update(ReportDto newReportDto, long id) throws NoSuchEntityException {
        List<JiraUser> jiraUsers = null;
        if (newReportDto.getAdmins() != null && newReportDto.getAdmins().size() > 0) {
            List<String> admins = newReportDto.getAdmins().stream().map(sd -> sd.getLogin()).collect(Collectors.toList());
            String[] logins = new String[admins.size()];
            jiraUsers = jiraUserRepository.findByLogins(admins.toArray(logins));
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
        return reportMapper.toDto(reportRepository.delete(id));
    }

}
