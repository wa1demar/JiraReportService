package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Comment;
import com.swansoftwaresolutions.jirareport.core.entity.Sprint;
import com.swansoftwaresolutions.jirareport.core.repository.SprintRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.sql.Select;
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
    public List<Sprint> getAllSprints() {
        return sessionFactory.getCurrentSession().createCriteria(Sprint.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<Sprint> getSprintsByIdReportId(Long reportId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Sprint.findByReportId");
        query.setParameter("reportId", reportId);

        @SuppressWarnings("unchecked")
        List<Sprint> sprints = (List<Sprint>) query.list();

        return sprints;
    }

    @Override
    public Sprint getSprintByIdReportAndIdAgileSprintId(Long reportId, Long agileSprintId) {
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
    public Sprint getSprintByIdAgileSprintId(Long agileSprintId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Sprint.findByAgileSprintId");
        query.setParameter("agileSprintId", agileSprintId);

        Sprint sprint = (Sprint) query.uniqueResult();
        return sprint;
    }

    @Override
    public void createSprint(Sprint sprint) {
        sessionFactory.getCurrentSession().save(sprint);
    }

    @Override
    public void updateSprint(Sprint sprint) {
        sessionFactory.getCurrentSession().update(sprint);
    }

    @Override
    public void deleteAllSprint() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Sprint.deleteAll");

        query.executeUpdate();
    }

    @Override
    public void deleteSprint(Sprint sprint) {
        sessionFactory.getCurrentSession().delete(sprint);
    }

    @Override
    public void deleteSprint(Long sprintId) {
        sessionFactory.getCurrentSession().delete(sprintId);
    }

    @Override
    public void deleteSprintsByIdReportId(Long reportId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Sprint.deleteByReportId");
        query.setParameter("reportId", reportId);

        query.executeUpdate();
    }
}