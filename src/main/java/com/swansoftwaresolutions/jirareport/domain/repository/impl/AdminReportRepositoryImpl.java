package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Admin;
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
    public List<Admin> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Admin.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<Admin> findByReportId(Long reportId) {
        return (List<Admin>) sessionFactory.openSession()
                .createCriteria(Config.class).add(Restrictions.eq("reportId", reportId));
    }

    @Override
    public Admin findById(Long id) {
        return (Admin) sessionFactory.openSession()
                .createCriteria(Config.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public Admin add(Admin admin) {
        sessionFactory.getCurrentSession().persist(admin);
        return admin;
    }

    @Override
    public Admin update(Admin admin) throws NoSuchEntityException {
        sessionFactory.getCurrentSession().update(admin);

        if (findById(admin.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (Admin) sessionFactory.openSession().merge(admin);
    }

    @Override
    public void delete(Admin admin) throws NoSuchEntityException {
        Admin adminR = findById(admin.getId());

        if (adminR != null) {
            sessionFactory.getCurrentSession().delete(admin);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void delete(Long id) throws NoSuchEntityException {
        Admin admin = findById(id);
        if (admin != null) {
            sessionFactory.getCurrentSession().delete(admin);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void deleteAll() throws NoSuchEntityException {
        List<Admin> admins = findAll();
        if (admins != null) {
            for (Admin admin : admins) {
                if (admin != null) {
                    sessionFactory.getCurrentSession().delete(admin);
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
        List<Admin> admins = findByReportId(reportId);
        if (admins != null) {
            for (Admin admin : admins) {
                if (admin != null) {
                    sessionFactory.getCurrentSession().delete(admin);
                } else {
                    throw new NoSuchEntityException("Entity Not Found");
                }
            }
        } else {
            throw new NoSuchEntityException("Entities Not Found");
        }
    }
}
