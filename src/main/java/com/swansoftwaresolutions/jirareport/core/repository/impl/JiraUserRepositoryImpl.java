package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */

@Repository
@Transactional
public class JiraUserRepositoryImpl implements JiraUserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public JiraUser add(JiraUser jiraUser) {
        sessionFactory.getCurrentSession().persist(jiraUser);
        return jiraUser;
    }

    @Override
    public JiraUser update(JiraUser user) throws NoSuchEntityException {
        if (user.getId() == null || findById(user.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (JiraUser) sessionFactory.openSession().merge(user);
    }

    @Override
    public JiraUser findById(long id) throws NoSuchEntityException {
        return (JiraUser) sessionFactory.openSession()
                .createCriteria(JiraUser.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public JiraUser findByName(String name) throws NoSuchEntityException {
        return (JiraUser) sessionFactory.openSession()
                .createCriteria(JiraUser.class).add(Restrictions.eq("fullName", name)).uniqueResult();
    }

    @Override
    public List<JiraUser> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(JiraUser.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public void delete(JiraUser jiraUser) throws NoSuchEntityException {
        if (jiraUser.getId() == null || findById(jiraUser.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        sessionFactory.getCurrentSession().delete(jiraUser);
    }

    @Override
    public void delete(Long jiraUserId) throws NoSuchEntityException {
        JiraUser user = findById(jiraUserId);
        if (user == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        sessionFactory.getCurrentSession().delete(user);
    }
}
