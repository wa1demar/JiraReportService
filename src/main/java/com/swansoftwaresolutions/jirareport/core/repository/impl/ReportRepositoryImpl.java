package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Report;
import com.swansoftwaresolutions.jirareport.core.repository.ReportRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Repository
@Transactional
public class ReportRepositoryImpl implements ReportRepository{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Report> getAllReports() {
        return sessionFactory.getCurrentSession().createCriteria(Report.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<Report> getAllOngoingReports() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Report.findAllAutomaticOngoingReport");

        @SuppressWarnings("unchecked")
        List<Report> adminReports = (List<Report>) query.list();
        return adminReports;
    }

    @Override
    public List<Report> getAllAutomaticOngoingReports() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Report.findAllAutomaticOngoingReport");

        @SuppressWarnings("unchecked")
        List<Report> adminReports = (List<Report>) query.list();
        return adminReports;
    }

    @Override
    public List<Report> getAllManualOngoingReports() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Report.findAllManualOngoingReport");

        @SuppressWarnings("unchecked")
        List<Report> adminReports = (List<Report>) query.list();
        return adminReports;
    }

    @Override
    public List<Report> getAllClosedReports() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Report.findAllClosed");

        @SuppressWarnings("unchecked")
        List<Report> reports = (List<Report>) query.list();

        return reports;
    }

    @Override
    public List<Report> getAllAutomaticClosedReports() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Report.findAllAutomaticClosedReport");

        @SuppressWarnings("unchecked")
        List<Report> reports = (List<Report>) query.list();

        return reports;
    }

    @Override
    public List<Report> getAllManualClosedReports() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Report.findAllManualClosedReports");

        @SuppressWarnings("unchecked")
        List<Report> reports = (List<Report>) query.list();

        return reports;
    }

    @Override
    public List<Report> getAllClosedReportsByDateClose(Date dateFrom, Date dateTo) {

        Query query = null;

        if (dateFrom != null && dateTo != null) {
            query = sessionFactory.getCurrentSession().getNamedQuery("Report.findAllClosedReportsByDateClose");
            query.setParameter("dateFrom", dateFrom);
            query.setParameter("dateTo", dateTo);
        } else if (dateFrom != null && dateTo == null) {
            query = sessionFactory.getCurrentSession().getNamedQuery("Report.findAllClosedReportsByDateCloseFrom");
            query.setParameter("dateFrom", dateFrom);
        } else if (dateFrom == null && dateTo != null) {
            query = sessionFactory.getCurrentSession().getNamedQuery("Report.findAllClosedReportsByDateCloseTo");
            query.setParameter("dateTo", dateTo);
        }

        @SuppressWarnings("unchecked")
        List<Report> reports = (List<Report>) query.list();

        return reports;
    }

    @Override
    public List<Report> getLastUpdatedReports() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Report.findLastUpdatedReport");

        @SuppressWarnings("unchecked")
        List<Report> reports = (List<Report>) query.list();

        return reports;
    }

    @Override
    public Report getReportById(Long id) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Report.findReportBytId");
        query.setParameter("id", id);

        @SuppressWarnings("unchecked")
        Report report = (Report) query.uniqueResult();

        return report;
    }

    @Override
    public Report getLastAddedReport() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Report.findLastAddedReport");

        @SuppressWarnings("unchecked")
        Report report = (Report) query.uniqueResult();

        return report;
    }

    @Override
    public void createReport(Report report) {
        sessionFactory.getCurrentSession().save(report);
    }

    @Override
    public void updateReport(Report report) {
        sessionFactory.getCurrentSession().update(report);
    }

    @Override
    public void deleteAllReport() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Report.deleteAll");
        query.executeUpdate();
    }

    @Override
    public void deleteReport(Report report) {
        sessionFactory.getCurrentSession().delete(report);
    }

    @Override
    public void deleteReport(Long reportId) {
        sessionFactory.getCurrentSession().delete(reportId);
    }
}
