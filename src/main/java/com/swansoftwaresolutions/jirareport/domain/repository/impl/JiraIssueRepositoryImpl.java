package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraBoardRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraIssueRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
public class JiraIssueRepositoryImpl implements JiraIssueRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<JiraIssue> findAll() throws NoSuchEntityException {
        return sessionFactory.getCurrentSession().createCriteria(JiraIssue.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<JiraIssue> findBySprintId(long sprintId) throws NoSuchEntityException {
        Query query = sessionFactory.getCurrentSession().createQuery("from JiraIssue issue where issue.sprintId = :sprintId");
        query.setParameter("sprintId", sprintId);
        return (List<JiraIssue>) query.list();
    }

    @Override
    public JiraIssue add(JiraIssue jiraIssue) {
        JiraIssue jiraIssueExist = null;
        try {
            jiraIssueExist = findByKey(jiraIssue.getKey());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        if (jiraIssueExist == null) {
            sessionFactory.getCurrentSession().save(jiraIssue);
        } else {
            jiraIssue.setId(jiraIssueExist.getId());
            sessionFactory.getCurrentSession().merge(jiraIssue);
        }

        return jiraIssue;
    }

    @Override
    public JiraIssue findById(Long jiraIssueId) throws NoSuchEntityException{
        return (JiraIssue) sessionFactory.getCurrentSession()
                .createCriteria(JiraIssue.class).add(Restrictions.eq("id", jiraIssueId)).uniqueResult();
    }

    @Override
    public JiraIssue findByKey(String issueKey) throws NoSuchEntityException {
        Query query = sessionFactory.getCurrentSession().createQuery("from JiraIssue issue where issue.key = :issueKey");
        query.setParameter("issueKey", issueKey);
        return (JiraIssue) query.uniqueResult();
    }

    @Override
    public void delete(JiraIssue jiraIssue) throws NoSuchEntityException {
        JiraIssue deletejiraIssue = findById(jiraIssue.getId());

        if (deletejiraIssue != null) {
            sessionFactory.getCurrentSession().delete(jiraIssue);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }

    }

    @Override
    public void delete(Long jiraIssueId) throws NoSuchEntityException {
        JiraIssue jiraIssue = findById(jiraIssueId);
        if (jiraIssue != null) {
            sessionFactory.getCurrentSession().delete(jiraIssue);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public JiraIssue update(JiraIssue jiraIssue) throws NoSuchEntityException {
        if (findById(jiraIssue.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (JiraIssue) sessionFactory.getCurrentSession().merge(jiraIssue);
    }
}
