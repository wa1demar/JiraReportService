package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.CacheProjectTotal;
import com.swansoftwaresolutions.jirareport.domain.repository.CacheProjectTotalRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Repository
@Transactional
public class CacheProjectTotalRepositoryImpl implements CacheProjectTotalRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public CacheProjectTotal add(CacheProjectTotal projectTotal) {
        sessionFactory.getCurrentSession().save(projectTotal);
        return projectTotal;
    }

    @Override
    public CacheProjectTotal update(CacheProjectTotal projectTotal) {
        sessionFactory.getCurrentSession().merge(projectTotal);
        return projectTotal;
    }

    @Override
    public CacheProjectTotal saveOrUpdate(CacheProjectTotal projectTotal) {
        CacheProjectTotal existedData = findByReportId(projectTotal.getReport().getId());
        CacheProjectTotal result = null;
        try {
            if (existedData != null) {
                projectTotal.setId(existedData.getId());
                result = update(projectTotal);
            } else {
                result = add(projectTotal);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        CacheProjectTotal data = findById(id);
        sessionFactory.getCurrentSession().delete(data);
    }

    @Override
    public CacheProjectTotal findById(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM CacheProjectTotal d WHERE d.id = :id");
        query.setParameter("id", id);

        return (CacheProjectTotal) query.uniqueResult();
    }

    @Override
    public CacheProjectTotal findByReportId(Long reportId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM CacheProjectTotal d WHERE d.report.id = :reportId");
        query.setParameter("reportId", reportId);

        return (CacheProjectTotal) query.uniqueResult();
    }

    @Override
    public void deleteByReportId(long reportId) {
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM CacheProjectTotal d WHERE d.report.id = :reportId");
        query.setParameter("reportId", reportId);
        query.executeUpdate();
    }

    @Override
    public List<CacheProjectTotal> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM CacheProjectTotal d WHERE d.report.isClosed != true ORDER BY d.id DESC");

        return query.list();
    }
}
