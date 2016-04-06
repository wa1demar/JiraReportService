package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraGroup;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraGroupRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Repository
@Transactional
public class JiraGroupRepositoryImpl implements JiraGroupRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(List<JiraGroup> groups) {
        for (JiraGroup group : groups) {
            JiraGroup existedGruup = findByName(group.getName());
            if (existedGruup == null) {
                sessionFactory.getCurrentSession().save(group);
            }
        }

    }

    @Override
    public JiraGroup findByName(String name) {
        Query query  = sessionFactory.getCurrentSession().createQuery("FROM JiraGroup g WHERE g.name = :name");
        query.setParameter("name", name);

        return (JiraGroup) query.uniqueResult();
    }

    @Override
    public List<JiraGroup> findAll() {
        Query query  = sessionFactory.getCurrentSession().createQuery("FROM JiraGroup g");
        return query.list();
    }
}
