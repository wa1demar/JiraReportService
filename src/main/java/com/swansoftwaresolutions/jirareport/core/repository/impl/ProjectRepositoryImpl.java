package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Project;
import com.swansoftwaresolutions.jirareport.core.repository.ProjectRepository;
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
public class ProjectRepositoryImpl implements ProjectRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Project add(Project project) {
        sessionFactory.getCurrentSession().persist(project);
        return project;
    }

    @Override
    public Project update(Project project) throws NoSuchEntityException {
        if (findById(project.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (Project) sessionFactory.openSession().merge(project);
    }

    @Override
    public List<Project> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Project.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public Project findById(Long projectId) {
        return (Project) sessionFactory.openSession()
                .createCriteria(Project.class).add(Restrictions.eq("id", projectId)).uniqueResult();
    }

    @Override
    public void delete(Project project) throws NoSuchEntityException {
        Project deleteProject = findById(project.getId());

        if (deleteProject != null) {
            sessionFactory.getCurrentSession().delete(project);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void delete(Long projectId) throws NoSuchEntityException {
        Project project = findById(projectId);
        if (project != null) {
            sessionFactory.getCurrentSession().delete(project);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }
}
