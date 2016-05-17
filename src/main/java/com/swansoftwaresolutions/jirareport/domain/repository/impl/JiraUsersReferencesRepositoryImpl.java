package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUsersReferences;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUsersReferencesRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Vladimir Martynyuk
 */
@Repository
public class JiraUsersReferencesRepositoryImpl implements JiraUsersReferencesRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public JiraUsersReferences add(JiraUsersReferences jiraUsersReferences) {
        sessionFactory.getCurrentSession().save(jiraUsersReferences);
        return jiraUsersReferences;
    }
}
