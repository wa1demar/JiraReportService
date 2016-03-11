package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.mapper.AdminReportMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.service.AdminReportService;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.core.dto.ReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.AdminReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.ReportResponceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    AdminReportService adminReportService;

    @Autowired
    AdminReportMapper adminReportMapper;


    @Override
    public List<ReportDto> findAll() {
        return reportMapper.toDtos(reportRepository.findAll());
    }

    @Override
    public ReportResponceDto save(ReportDto reportNew) throws NoSuchEntityException {
        return reportMapper.toResponceDto(reportMapper.toDto(reportRepository.add(reportMapper.fromDto(prepareReportDto(reportNew)))));
    }

    @Override
    public Report update(Report report) throws NoSuchEntityException {
        return reportRepository.update(report);
    }

    @Override
    public ReportDto findById(long id) throws NoSuchEntityException {
        return reportMapper.toDto(reportRepository.findById(id));
    }

    @Override
    public void delete(ReportDto reportDto) throws NoSuchEntityException {
        reportRepository.delete(reportMapper.fromDto(reportDto));
    }

    @Override
    public void deleteById(long id) throws NoSuchEntityException {
        reportRepository.delete(id);
    }

    private ReportDto prepareReportDto(ReportDto reportNew) throws NoSuchEntityException {
        JiraUserDto jiraUser = jiraUserMapper.toDto(jiraUserRepository.findByLogin(reportNew.getCreator()));

        List<AdminReportDto> adminReportDtos = new ArrayList<>();
        for (AdminReportDto adminReportDto : reportNew.getAdmins()){
            adminReportDto = adminReportMapper.toDtofromJiraUser(jiraUserRepository.findByLogin(adminReportDto.getLogin()));
            adminReportDto.setId(null);
            adminReportDtos.add(adminReportDto);
        }

        ReportDto reportDto = new ReportDto();
        reportDto.setTitle(reportNew.getTitle());
        reportDto.setBoardId(reportNew.getBoardId());
        reportDto.setTypeId(reportNew.getTypeId());
        reportDto.setCreator(jiraUser.getLogin());
        reportDto.setCreatorId(jiraUser.getId());
        reportDto.setCreatedDate(new Date());
        reportDto.setAdminReports(adminReportDtos);
        reportDto.setIsClosed(false);

        return reportDto;
    }
}
