package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Project;
import com.swansoftwaresolutions.jirareport.core.repository.ProjectRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vladimir Martynyuk
 */

@Repository
@Transactional
public class ProjectRepositoryImpl implements ProjectRepository {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Project add(Project project) {
        sessionFactory.getCurrentSession().persist(project);
        return project;
    }
}
