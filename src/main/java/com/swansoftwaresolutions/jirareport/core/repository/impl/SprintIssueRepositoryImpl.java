package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.SprintIssue;
import com.swansoftwaresolutions.jirareport.core.repository.SprintIssueRepository;
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
public class SprintIssueRepositoryImpl implements SprintIssueRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<SprintIssue> getAllSprintIssues() {
        return sessionFactory.getCurrentSession().createCriteria(SprintIssue.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<SprintIssue> getSprintIssuesBySprintId(Long sprintId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintIssue.findBySprintId");

        @SuppressWarnings("unchecked")
        List<SprintIssue> sprintIssues = (List<SprintIssue>) query.list();
        return sprintIssues;
    }

    @Override
    public List<SprintIssue> getSprintIssuesBySprintIdAndAssignee(Long sprintId, String assignee) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintIssue.findBySprintIdAndAssignee");

        @SuppressWarnings("unchecked")
        List<SprintIssue> sprintIssues = (List<SprintIssue>) query.list();
        return sprintIssues;
    }

    @Override
    public List<SprintIssue> getSprintIssuesBySprintIdAndAssigneeAndIssueDate(Long sprintId, String assignee, String issueDate) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintIssue.findBySprintIdAndAssigneeAndIssueDate");

        @SuppressWarnings("unchecked")
        List<SprintIssue> sprintIssues = (List<SprintIssue>) query.list();
        return sprintIssues;
    }

    @Override
    public SprintIssue getSprintIssueById(Long id) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintIssue.findById");

        @SuppressWarnings("unchecked")
        SprintIssue sprintIssue = (SprintIssue) query.uniqueResult();
        return sprintIssue;
    }

    @Override
    public void createSprintIssue(SprintIssue sprintIssue) {
        sessionFactory.getCurrentSession().save(sprintIssue);
    }

    @Override
    public void updateSprintIssue(SprintIssue sprintIssue) {
        sessionFactory.getCurrentSession().update(sprintIssue);
    }

    @Override
    public void deleteAllSprintIssue() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintIssue.deleteAll");
        query.executeUpdate();
    }

    @Override
    public void deleteSprintIssue(SprintIssue sprintIssue) {
        sessionFactory.getCurrentSession().delete(sprintIssue);
    }

    @Override
    public void deleteSprintIssue(Long id) {
        sessionFactory.getCurrentSession().delete(id);
    }

    @Override
    public void deleteSprintIssuesByIdSprint(Long sprintId) {
        sessionFactory.getCurrentSession().delete(sprintId);
    }

    @Override
    public void deleteSprintIssuesBySprintIdAndAssignee(Long sprintId, String assignee) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintIssue.deleteBySprintIdAndAssignee");
        query.setParameter("sprintId", sprintId);
        query.setParameter("assignee", assignee);
        query.executeUpdate();
    }
}
