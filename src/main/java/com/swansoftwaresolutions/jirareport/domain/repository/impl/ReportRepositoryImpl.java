package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    public List<Report> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Report.class).list();
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
    public Report findById(Long id) {
        return (Report) sessionFactory.openSession()
                .createCriteria(Report.class).add(Restrictions.eq("id", id)).uniqueResult();

    }

    @Override
    public Report getLastAddedReport() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Report.findLastAddedReport");

        @SuppressWarnings("unchecked")
        Report report = (Report) query.uniqueResult();

        return report;
    }

    @Override
    public Report add(Report report) {
        sessionFactory.getCurrentSession().save(report);
        return report;
    }

    @Override
    public Report update(Report report) throws NoSuchEntityException {
        if (findById(report.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (Report) sessionFactory.openSession().merge(report);
    }

    @Override
    public void deleteAll() throws NoSuchEntityException {
    List<Report> reports = findAll();
        for (Report report : reports){
            delete(report);
        }
    }

    @Override
    public void delete(Report report)  throws NoSuchEntityException {
        Report deleteReport = findById(report.getId());

        if (deleteReport != null) {
            sessionFactory.getCurrentSession().delete(report);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void delete(Long reportId) throws NoSuchEntityException {
        Report report = findById(reportId);
        if (report != null) {
            sessionFactory.getCurrentSession().delete(report);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

}
