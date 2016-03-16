package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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
//        if (user.getId() == null || findById(user.getId()) == null) {
//            throw new NoSuchEntityException("Entity not found");
//        }
        return (JiraUser) sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    public JiraUser findByLogin(String login) throws NoSuchEntityException {
        return (JiraUser) sessionFactory.getCurrentSession()
                .createCriteria(JiraUser.class).add(Restrictions.eq("login", login)).uniqueResult();
    }

    @Override
    public List<JiraUser> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(JiraUser.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public void delete(JiraUser jiraUser) throws NoSuchEntityException {
        sessionFactory.getCurrentSession().delete(jiraUser);
    }

    @Override
    public List<JiraUser> findByLogins(String[] admins) {
        return  sessionFactory.getCurrentSession().createCriteria(JiraUser.class).add(Restrictions.in("login", admins)).list();
    }
}
