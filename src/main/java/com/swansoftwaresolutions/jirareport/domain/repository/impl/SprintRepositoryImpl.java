package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
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
public class SprintRepositoryImpl implements SprintRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Sprint add(Sprint sprint) {
        sessionFactory.getCurrentSession().save(sprint);
        return sprint;
    }

    @Override
    public Sprint update(Sprint sprint) {
        sessionFactory.getCurrentSession().update(sprint);
        return sprint;
    }

    @Override
    public List<Sprint> findByReportId(long reportId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Sprint s WHERE s.report.id = :reportId ORDER BY s.id ASC");
        query.setParameter("reportId", reportId);

        return query.list();
    }

    @Override
    public Sprint delete(long sprintId) throws NoSuchEntityException {
        Sprint sprint = findById(sprintId);
        if (sprint != null) {
            sessionFactory.getCurrentSession().delete(sprint);
        } else {
            throw new NoSuchEntityException("Sprint not found");
        }
        return sprint;
    }

    @Override
    public Sprint findById(long sprintId) throws NoSuchEntityException {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Sprint s WHERE s.id = :sprintId");
        query.setParameter("sprintId", sprintId);
        Sprint sprint = (Sprint) query.uniqueResult();
        if (sprint  == null) {
            throw new NoSuchEntityException("Sprint not found");
        }
        return sprint;
    }


}
