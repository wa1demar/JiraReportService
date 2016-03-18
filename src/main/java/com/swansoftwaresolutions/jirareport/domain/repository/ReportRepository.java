package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ReportRepository {

    List<Report> findAll();

    List<Report> getAllOngoingReports();

    List<Report> getAllAutomaticOngoingReports();

    List<Report> getAllManualOngoingReports();

    List<Report> getAllClosedReports();

    List<Report> getAllAutomaticClosedReports();

    List<Report> getAllManualClosedReports();

    List<Report> getAllClosedReportsByDateClose(final Date dateFrom, final Date dateTo);

    List<Report> getLastUpdatedReports();

    Report findById(final Long id);

    Report getLastAddedReport();

    Report add(Report report);

    Report update(Report report) throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;

    Report delete(Report report) throws NoSuchEntityException;

    Report delete(Long reportId) throws NoSuchEntityException;

    List<Report> findAllClosed();
}
