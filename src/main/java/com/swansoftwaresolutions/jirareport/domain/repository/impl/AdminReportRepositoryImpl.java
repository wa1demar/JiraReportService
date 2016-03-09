package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.AdminReport;
import com.swansoftwaresolutions.jirareport.domain.entity.Config;
import com.swansoftwaresolutions.jirareport.domain.repository.AdminReportRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    public List<AdminReport> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(AdminReport.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<AdminReport> findByReportId(Long reportId) {
        return (List<AdminReport>) sessionFactory.openSession()
                .createCriteria(Config.class).add(Restrictions.eq("reportId", reportId));
    }

    @Override
    public AdminReport findById(Long id) {
        return (AdminReport) sessionFactory.openSession()
                .createCriteria(Config.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public AdminReport add(AdminReport adminReport) {
        sessionFactory.getCurrentSession().persist(adminReport);
        return adminReport;
    }

    @Override
    public AdminReport update(AdminReport adminReport) throws NoSuchEntityException {
        sessionFactory.getCurrentSession().update(adminReport);

        if (findById(adminReport.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (AdminReport) sessionFactory.openSession().merge(adminReport);
    }

    @Override
    public void delete(AdminReport adminReport) throws NoSuchEntityException {
        AdminReport adminR = findById(adminReport.getId());

        if (adminR != null) {
            sessionFactory.getCurrentSession().delete(adminReport);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void delete(Long id) throws NoSuchEntityException {
        AdminReport adminReport = findById(id);
        if (adminReport != null) {
            sessionFactory.getCurrentSession().delete(adminReport);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void deleteAll() throws NoSuchEntityException {
        List<AdminReport> adminReports = findAll();
        if (adminReports != null) {
            for (AdminReport adminReport : adminReports) {
                if (adminReport != null) {
                    sessionFactory.getCurrentSession().delete(adminReport);
                } else {
                    throw new NoSuchEntityException("Entity Not Found");
                }
            }
        } else {
            throw new NoSuchEntityException("Entities Not Found");
        }
    }

    @Override
    public void deleteByReportId(Long reportId) throws NoSuchEntityException {
        List<AdminReport> adminReports = findByReportId(reportId);
        if (adminReports != null) {
            for (AdminReport adminReport : adminReports) {
                if (adminReport != null) {
                    sessionFactory.getCurrentSession().delete(adminReport);
                } else {
                    throw new NoSuchEntityException("Entity Not Found");
                }
            }
        } else {
            throw new NoSuchEntityException("Entities Not Found");
        }
    }
}
