package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDtoBuilder;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.entity.builder.JiraUserBuilder;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    JiraUserMapper jiraUserMapper;

    @Autowired
    JiraUserRepository jiraUserRepository;


    @Override
    public ReportListDto retrieveAllReportsList() {
        List<ReportDto> reportDtos = reportMapper.toDtos(reportRepository.findAll());

        return new ReportListDtoBuilder().reportsDto(reportDtos).build();
    }

    @Override
    public ReportDto add(NewReportDto newReportDto) throws NoSuchEntityException {

        List<JiraUser> jiraUsers = jiraUserRepository.findByLogins(newReportDto.getAdmins());

        Report newReport = reportMapper.fromDto(newReportDto);
        newReport.setAdmins(jiraUsers);

        Report addedReport = reportRepository.add(newReport);

        return reportMapper.toDto(addedReport);
    }

    @Override
    public ReportDto retrieveReportByID(long id) {
        return reportMapper.toDto(reportRepository.findById(id));
    }

    @Override
    public ReportDto update(NewReportDto newReportDto, long id) throws NoSuchEntityException {
        List<JiraUser> jiraUsers = null;
        if (newReportDto.getAdmins() != null && newReportDto.getAdmins().length > 0) {
            jiraUsers = jiraUserRepository.findByLogins(newReportDto.getAdmins());
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
    public void delete(long id) throws NoSuchEntityException {
        reportRepository.delete(id);
    }

}
