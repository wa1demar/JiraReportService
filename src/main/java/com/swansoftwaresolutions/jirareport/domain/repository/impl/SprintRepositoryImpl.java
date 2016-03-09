package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
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
    public List<Sprint> findByReportId(Long reportId) {
        return (List<Sprint>) sessionFactory.openSession()
                .createCriteria(Sprint.class).add(Restrictions.eq("reportId", reportId)).list();
    }

    @Override
    public Sprint findByReportIdAndAgileSprintId(Long reportId, Long agileSprintId) {
        return (Sprint) sessionFactory.openSession()
                .createCriteria(Sprint.class).add(Restrictions.eq("reportId", reportId)).add(Restrictions.eq("agileSprintId", agileSprintId)).uniqueResult();
    }

    @Override
    public Sprint findById(Long id) {
        return (Sprint) sessionFactory.openSession()
                .createCriteria(Sprint.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public Sprint findLast() {
        Long id = (Long) sessionFactory.getCurrentSession().createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult();
        return findById(id);
    }

    @Override
    public Sprint findByAgileSprintId(Long agileSprintId) {
        return (Sprint) sessionFactory.openSession()
                .createCriteria(Sprint.class).add(Restrictions.eq("agileSprintId", agileSprintId)).uniqueResult();
    }

    @Override
    public Sprint add(Sprint sprint) {
        sessionFactory.getCurrentSession().save(sprint);
        return sprint;
    }

    @Override
    public Sprint update(Sprint sprint) throws NoSuchEntityException{
        sessionFactory.getCurrentSession().update(sprint);
        if (findById(sprint.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }

        return (Sprint) sessionFactory.openSession().merge(sprint);
    }

    @Override
    public void deleteAll() throws NoSuchEntityException{
        List<Sprint> sprintList = findAll();
        if (sprintList != null) {
            for (Sprint sprint : sprintList) {
                if (sprint != null) {
                    sessionFactory.getCurrentSession().delete(sprint);
                } else {
                    throw new NoSuchEntityException("Entity Not Found");
                }
            }
        } else {
            throw new NoSuchEntityException("Entities Not Found");
        }
    }

    @Override
    public void delete(Sprint sprint) throws NoSuchEntityException{
        Sprint deleteSprint = findById(sprint.getId());
        if (deleteSprint != null) {
            sessionFactory.getCurrentSession().delete(sprint);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void delete(Long id) throws NoSuchEntityException{
        Sprint sprint = findById(id);
        if (sprint != null) {
            sessionFactory.getCurrentSession().delete(sprint);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void deleteByReportId(Long reportId) throws NoSuchEntityException{
        List<Sprint> sprintList = findByReportId(reportId);
        if (sprintList != null) {
            for (Sprint sprint : sprintList) {
                if (sprint != null) {
                    sessionFactory.getCurrentSession().delete(sprint);
                } else {
                    throw new NoSuchEntityException("Entity Not Found");
                }
            }
        } else {
            throw new NoSuchEntityException("Entities Not Found");
        }
    }
}
