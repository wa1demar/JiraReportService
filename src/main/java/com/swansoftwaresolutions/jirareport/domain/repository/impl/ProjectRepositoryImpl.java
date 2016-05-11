package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.domain.repository.ProjectRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Project save(Project project) {
        sessionFactory.getCurrentSession().save(project);
        return project;
    }

    @Override
    public List<Project> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Project p");

        return query.list();
    }
}
