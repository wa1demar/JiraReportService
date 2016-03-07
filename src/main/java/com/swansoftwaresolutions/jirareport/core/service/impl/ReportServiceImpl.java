package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Report;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.core.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.core.repository.ReportRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import com.swansoftwaresolutions.jirareport.rest.dto.NewReportDto;
import com.swansoftwaresolutions.jirareport.rest.dto.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    JiraUserRepository jiraUserRepository;


    @Override
    public List<ReportDto> findAll() {
        return reportMapper.toDtos(reportRepository.findAll());
    }

    @Override
    public NewReportDto save(NewReportDto newReportDto) {
        newReportDto.setCreatedDate(new Date());

        return reportMapper.toNewDto(reportRepository.add(reportMapper.fromNewDto(newReportDto)));
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
    public NewReportDto findNewReportById(long id) throws NoSuchEntityException {
        return reportMapper.toNewDto(reportRepository.findById(id));
    }

    @Override
    public void delete(ReportDto reportDto) throws NoSuchEntityException {
        reportRepository.delete(reportMapper.fromDto(reportDto));
    }

    @Override
    public void deleteById(long id) throws NoSuchEntityException {
        reportRepository.delete(id);
    }
}
