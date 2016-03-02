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
    public List<JiraUser> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(JiraUser.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public JiraUser findById(Long projectId) {
        return (JiraUser) sessionFactory.openSession()
                .createCriteria(JiraUser.class).add(Restrictions.eq("id", projectId)).uniqueResult();
    }

    @Override
    public void delete(JiraUser user) throws NoSuchEntityException {
        JiraUser deleteUser = findById(user.getId());

        if (deleteUser != null) {
            sessionFactory.getCurrentSession().delete(user);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }

    }

    @Override
    public void delete(Long userId) throws NoSuchEntityException {
        JiraUser user = findById(userId);
        if (user != null) {
            sessionFactory.getCurrentSession().delete(user);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public JiraUser update(JiraUser user) throws NoSuchEntityException {
        if (findById(user.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (JiraUser) sessionFactory.openSession().merge(user);
    }
}
