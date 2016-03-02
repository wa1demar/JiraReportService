package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Project;
import com.swansoftwaresolutions.jirareport.core.repository.ProjectRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
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
public class ProjectRepositoryImpl implements ProjectRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Project add(Project project) {
        sessionFactory.getCurrentSession().persist(project);
        return project;
    }

    @Override
    public List<Project> getAllProjects() {
        return sessionFactory.getCurrentSession().createCriteria(Project.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

    }

    @Override
    public void deleteProject(Project project) {
        sessionFactory.getCurrentSession().delete(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        sessionFactory.getCurrentSession().delete(projectId);
    }
}
