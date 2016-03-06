package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Report;
import com.swansoftwaresolutions.jirareport.core.mapper.ReportMapper;
import com.swansoftwaresolutions.jirareport.core.repository.ReportRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    ReportRepository reportRepository;

    @Override
    public Report save(Report project) {
        return reportRepository.add(project);
    }

    @Override
    public Report update(Report report) throws NoSuchEntityException {
        return reportRepository.update(report);
    }
}
