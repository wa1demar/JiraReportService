package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.AdminReport;
import com.swansoftwaresolutions.jirareport.core.repository.AdminReportRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
@Repository
@Transactional
public class AdminReportRepositoryImpl implements AdminReportRepository {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<AdminReport> getAllAdminReports() {
        return sessionFactory.getCurrentSession().createCriteria(AdminReport.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<AdminReport> getAdminReportsByReportId(Long reportId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("AdminReport.findByReportId");
        query.setParameter("reportId", reportId);

        @SuppressWarnings("unchecked")
        List<AdminReport> adminReports = (List<AdminReport>) query.list();
        return adminReports;
    }

    @Override
    public AdminReport getAdminReportById(Long id) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("AdminReport.findById");
        query.setParameter("id", id);

        @SuppressWarnings("unchecked")
        AdminReport adminReport = (AdminReport) query.uniqueResult();

        return adminReport;
    }

    @Override
    public void createAdminReport(AdminReport adminReport) {
        sessionFactory.getCurrentSession().save(adminReport);
    }

    @Override
    public void updateAdminReport(AdminReport adminReport) {
        sessionFactory.getCurrentSession().update(adminReport);
    }

    @Override
    public void deleteAdminReport(AdminReport adminReport) {
        sessionFactory.getCurrentSession().delete(adminReport);
    }

    @Override
    public void deleteAllAdminReport() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("AdminReport.deleteAll.deleteAll");
        query.executeUpdate();
    }

    @Override
    public void deleteAdminReport(Long id) {
        sessionFactory.getCurrentSession().delete(id);
    }

    @Override
    public void deleteAdminReportsByIdReport(Long reportId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("AdminReport.deleteByReportId");
        query.setParameter("reportId", reportId);
        query.executeUpdate();
    }
}
