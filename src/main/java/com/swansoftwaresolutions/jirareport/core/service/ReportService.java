package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */
public interface ReportService {

    ReportDto add(NewReportDto newReportDto) throws NoSuchEntityException;

    ReportDto update(NewReportDto report, long id) throws NoSuchEntityException;

    ReportDto delete(long id) throws NoSuchEntityException;

    void delete(ReportDto reportDto) throws NoSuchEntityException;

    ReportListDto retrieveAllReportsList();

    ReportListDto retrieveAllClosedReportsList();

    ReportDto copy(long id);
    
    ReportDto findById(long id);

    ReportDto findByBoardId(Long id) throws NoSuchEntityException;


}
