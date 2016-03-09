package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Comment;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
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
public class SprintRepositoryImpl implements SprintRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Sprint> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Sprint.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<Sprint> findSprintsByReportId(Long reportId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Sprint.findByReportId");
        query.setParameter("reportId", reportId);

        @SuppressWarnings("unchecked")
        List<Sprint> sprints = (List<Sprint>) query.list();

        return sprints;
    }

    @Override
    public Sprint getSprintByReportIdAndAgileSprintId(Long reportId, Long agileSprintId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Sprint.findByReportIdAndAgileSprintId");
        query.setParameter("reportId", reportId);
        query.setParameter("agileSprintId", agileSprintId);

        Sprint sprint = (Sprint) query.uniqueResult();
        return sprint;
    }

    @Override
    public Sprint getSprintById(Long id) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Sprint.findById");
        query.setParameter("id", id);

        Sprint sprint = (Sprint) query.uniqueResult();
        return sprint;
    }

    @Override
    public Sprint getLastAddedSprint() {
        Long id = (Long) sessionFactory.getCurrentSession().createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult();
        return getSprintById(id);
    }

    @Override
    public Sprint getSprintByAgileSprintId(Long agileSprintId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Sprint.findByAgileSprintId");
        query.setParameter("agileSprintId", agileSprintId);

        Sprint sprint = (Sprint) query.uniqueResult();
        return sprint;
    }

    @Override
    public Sprint add(Sprint sprint) {
        sessionFactory.getCurrentSession().save(sprint);
        return sprint;
    }

    @Override
    public void update(Sprint sprint) {
        sessionFactory.getCurrentSession().update(sprint);
    }

    @Override
    public void deleteAllSprint() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Sprint.deleteAll");

        query.executeUpdate();
    }

    @Override
    public void delete(Sprint sprint) {
        sessionFactory.getCurrentSession().delete(sprint);
    }

    @Override
    public void delete(Long sprintId) {
        sessionFactory.getCurrentSession().delete(sprintId);
    }

    @Override
    public void deleteSprintsByReportId(Long reportId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Sprint.deleteByReportId");
        query.setParameter("reportId", reportId);

        query.executeUpdate();
    }
}
