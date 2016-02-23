package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.AdminReport;

import java.util.List;

public interface AdminReportRepository {

    List<AdminReport> getAllAdminReports();

    List<AdminReport> getAdminReportsByReportId(final Long reportId);

    AdminReport getAdminReportById(final Long id);

    void createAdminReport(final AdminReport adminReport);

    void updateAdminReport(final AdminReport adminReport);

    void deleteAdminReport(final AdminReport adminReport);

    void deleteAllAdminReport();

    void deleteAdminReport(final Long id);

    void deleteAdminReportsByIdReport(final Long reportId);
}
