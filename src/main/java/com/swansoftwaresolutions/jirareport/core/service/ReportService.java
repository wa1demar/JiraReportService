package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.dashboard.ProjectDashboardDto;
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

    ReportListDto retrieveAllClosedReportsListPaginated(int page);

    ReportDto copy(long id) throws NoSuchEntityException;

    ReportDto findByBoardId(Long id) throws NoSuchEntityException;


    ReportListDto retrieveAllOngoingReportsList();

    ReportListDto retrieveAllOngoingReportsListPaginated(int page);

    ProjectDashboardDto findProjectDashboard(Long id);

    long getClosedSprintCount(Long reportId);

    long getSprintCount(Long reportId);

    boolean showUat(Long reportId);
}
