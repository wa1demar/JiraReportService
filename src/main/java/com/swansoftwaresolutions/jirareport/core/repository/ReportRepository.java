package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.Report;

import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ReportRepository {

    List<Report> getAllReports();

    List<Report> getAllOngoingReports();

    List<Report> getAllAutomaticOngoingReports();

    List<Report> getAllManualOngoingReports();

    List<Report> getAllClosedReports();

    List<Report> getAllAutomaticClosedReports();

    List<Report> getAllManualClosedReports();

    List<Report> getAllClosedReportsByDateClose(final Date dateFrom, final Date dateTo);

    List<Report> getLastUpdatedReports();

    Report getReportById(final Long id);

    Report getLastAddedReport();

    void createReport(final Report report);

    void updateReport(final Report report);

    void deleteAllReport();

    void deleteReport(final Report report);

    void deleteReport(final Long reportId);
}
