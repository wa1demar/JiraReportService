package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraGroup;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

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
        sessionFactory.getCurrentSession().save(jiraUser);
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
        return sessionFactory.getCurrentSession().createCriteria(JiraUser.class).add(Restrictions.in("login", admins)).list();
    }

    @Override
    public void saveAll(List<JiraUser> jiraUsers, JiraGroup group) {
        JiraUser existed = null;
        for (JiraUser user : jiraUsers) {
            try {
                existed = findByLogin(user.getLogin());
            } catch (NoSuchEntityException ex) {
                // TODO: new user
            }

            if (existed == null) {
                user.setGroups(new ArrayList<JiraGroup>() {{
                    add(group);
                }});
                add(user);
            } else {
                existed.setEmail(user.getEmail());
                existed.setFullName(user.getFullName());

                Set<JiraGroup> groups = new HashSet<>(existed.getGroups());
                List<JiraUser> existedusers = group.getUsers();
                existedusers.add(existed);
                group.setUsers(existedusers);
                groups.add(group);

                existed.setGroups(new ArrayList<>(groups));

                sessionFactory.getCurrentSession().update(existed);
                sessionFactory.getCurrentSession().flush();

            }
        }
    }

    @Override
    public List<JiraUser> findByGroups(String[] groups) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT u FROM JiraUser u INNER JOIN u.groups g WHERE g.name IN :groups");
        query.setParameterList("groups", Arrays.asList(groups));

        return query.list();
    }
}
