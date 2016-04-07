package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;
import com.swansoftwaresolutions.jirareport.domain.repository.DueDateRepository;
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
public class DueDateRepositoryImpl implements DueDateRepository {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<JiraIssue> retrieveAllDueDatas() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM JiraIssue i WHERE (i.dueDates.size > 1) and (i.statusName != 'closed')");

        return query.list();
    }
}
