package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraBoardRepository;
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
public class JiraBoardRepositoryImpl implements JiraBoardRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<JiraBoard> findAll() throws NoSuchEntityException {
        return sessionFactory.getCurrentSession().createCriteria(JiraBoard.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list(); }

    @Override
    public JiraBoard add(JiraBoard jiraBoard) {
        sessionFactory.getCurrentSession().persist(jiraBoard);
        return jiraBoard;
    }

    @Override
    public JiraBoard findById(Long jiraBoardId) {
        return (JiraBoard) sessionFactory.getCurrentSession()
                .createCriteria(JiraBoard.class).add(Restrictions.eq("id", jiraBoardId)).uniqueResult();

    }

    @Override
    public void delete(JiraBoard jiraBoard) throws NoSuchEntityException {
        JiraBoard deleteJiraBoard = findById(jiraBoard.getId());

        if (deleteJiraBoard != null) {
            sessionFactory.getCurrentSession().delete(jiraBoard);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }

    }

    @Override
    public void delete(Long jiraBoardId) throws NoSuchEntityException {
        JiraBoard jiraBoard = findById(jiraBoardId);
        if (jiraBoard != null) {
            sessionFactory.getCurrentSession().delete(jiraBoard);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public JiraBoard update(JiraBoard jiraBoard) throws NoSuchEntityException {
        if (findById(jiraBoard.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (JiraBoard) sessionFactory.getCurrentSession().merge(jiraBoard);
    }
}
