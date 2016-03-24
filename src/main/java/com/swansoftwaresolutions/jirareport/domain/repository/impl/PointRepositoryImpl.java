package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraPoint;
import com.swansoftwaresolutions.jirareport.domain.repository.PointRepository;
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
public class PointRepositoryImpl implements PointRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<JiraPoint> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(JiraPoint.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public JiraPoint add(JiraPoint jiraPoint) {
        JiraPoint jiraPointExist = new JiraPoint();
        try {
            jiraPointExist = findByLoginAndSprintId(jiraPoint.getUserLogin(), jiraPoint.getSprintId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        if (jiraPointExist == null) {
            sessionFactory.getCurrentSession().persist(jiraPoint);
        } else {
            sessionFactory.getCurrentSession().merge(jiraPoint);
        }

        return jiraPoint;
    }

    @Override
    public JiraPoint findById(Long id) throws NoSuchEntityException {
        return (JiraPoint) sessionFactory.getCurrentSession()
                .createCriteria(JiraPoint.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public JiraPoint update(JiraPoint jiraPoint) throws NoSuchEntityException {
        if (findById(jiraPoint.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }

        sessionFactory.getCurrentSession().update(jiraPoint);

        return jiraPoint;
    }

    @Override
    public void deleteAll() throws NoSuchEntityException {
        List<JiraPoint> reports = findAll();
        for (JiraPoint report : reports) {
            delete(report);
        }
    }

    @Override
    public JiraPoint delete(JiraPoint report) throws NoSuchEntityException {
        JiraPoint deleteJiraPoint = findById(report.getId());

        if (deleteJiraPoint != null) {
            sessionFactory.getCurrentSession().delete(report);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }

        return deleteJiraPoint;
    }

    @Override
    public JiraPoint delete(Long reportId) throws NoSuchEntityException {
        JiraPoint jiraPoint = findById(reportId);
        if (jiraPoint != null) {
            sessionFactory.getCurrentSession().delete(jiraPoint);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }

        return jiraPoint;
    }

    @Override
    public JiraPoint findByLoginAndSprintId(String login, Long sprint) throws NoSuchEntityException {
        return (JiraPoint) sessionFactory.getCurrentSession()
                .createCriteria(JiraPoint.class).add(Restrictions.eq("userLogin", login)).add(Restrictions.eq("sprintId", sprint)).uniqueResult();
    }

    @Override
    public List<JiraPoint> findByBoardId(Long boardId) throws NoSuchEntityException {
        return (List<JiraPoint>) sessionFactory.getCurrentSession()
                .createCriteria(JiraPoint.class).add(Restrictions.eq("boardId", boardId)).list();
    }
}
