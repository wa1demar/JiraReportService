package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraIssueRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<JiraIssue> findBySprintIds(List<Long> ids) {
        Query query = sessionFactory.getCurrentSession().createQuery("from JiraIssue i WHERE i.sprintId in :ids");
        query.setParameterList("ids", ids);

        return query.list();
    }

    @Override
    public void saveAll(List<JiraIssue> jiraIssues) {
        if (jiraIssues == null || jiraIssues.size() == 0){
            return;
        }

        List<String> keys = jiraIssues.stream().map(r -> r.getKey()).collect(Collectors.toList());
        Query query = sessionFactory.getCurrentSession().createQuery("FROM JiraIssue ji WHERE ji.key IN :keys");
        query.setParameterList("keys", keys);
        List<JiraIssue> existed = query.list();
        Map<String, JiraIssue> issueMap = existed.stream().collect(Collectors.toMap(JiraIssue::getKey, b -> b));


        Session session = sessionFactory.openSession();

        try {
            for (JiraIssue issue : jiraIssues) {
                JiraIssue existedIssue = issueMap.get(issue.getKey());

                if (existedIssue == null) {
                    session.save(issue);
                } else {
                    issue.setId(existedIssue.getId());
                    session.update(issue);
                }
            }

            session.flush();
        } finally {
            session.close();
        }
    }
}
