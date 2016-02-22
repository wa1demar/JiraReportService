package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.AdminReport;

import java.util.List;

public interface ReportAdminRepository {

    List<AdminReport> getAllReportAdmins();

    List<AdminReport> getReportAdminsByIdReport(final Long reportId);

    AdminReport getReportAdminById(final Long id);

    void createReportAdmin(final AdminReport adminReport);

    void updateReportAdmin(final AdminReport adminReport);

    void deleteReportAdmin(final AdminReport adminReport);

    void deleteAllReportAdmin();

    void deleteReportAdmin(final Long id);

    void deleteReportAdminsByIdReport(final Long reportId);
}
