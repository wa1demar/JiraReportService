package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.ProjectDasboard.ProjectDashboardDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportListDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;

/**
 * @author Vitaliy Holovko
 */
public interface ReportService {

    ReportDto add(NewReportDto newReportDto) throws NoSuchEntityException;

    ReportDto update(NewReportDto report, long id) throws NoSuchEntityException;

    ReportDto delete(long id) throws NoSuchEntityException;

    void delete(ReportDto reportDto) throws NoSuchEntityException;

    ReportListDto retrieveAllReportsList();

    ReportDto retrieveReportByID(long id) throws NoSuchEntityException;

    ReportListDto retrieveAllClosedReportsList();

    ReportDto copy(long id) throws NoSuchEntityException;

    ReportDto findByBoardId(Long id) throws NoSuchEntityException;


    ReportListDto retrieveAllOngoingReportsList();

    ProjectDashboardDto findProjectDashboard(Long id);
}
