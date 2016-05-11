package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraProjectRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
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
public class JiraProjectRepositoryImpl implements JiraProjectRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public JiraProject add(JiraProject jiraProject) {
        sessionFactory.getCurrentSession().save(jiraProject);
        return jiraProject;
    }

    @Override
    public JiraProject update(JiraProject jiraProject) throws NoSuchEntityException {
        if (findById(jiraProject.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (JiraProject) sessionFactory.getCurrentSession().merge(jiraProject);
    }

    @Override
    public void save(List<JiraProject> projects) {
        for (JiraProject project : projects) {
            JiraProject existedProject = null;
            try {
                existedProject = findByKey(project.getKey());
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }

            if (existedProject != null) {
                    existedProject.setKey(project.getKey());
                    existedProject.setName(project.getName());
                    sessionFactory.getCurrentSession().update(existedProject);
            } else {
                add(project);
            }
        }
    }

    @Override
    public List<JiraProject> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(JiraProject.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public JiraProject findById(Long projectId) {
        return (JiraProject) sessionFactory.getCurrentSession()
                .createCriteria(JiraProject.class).add(Restrictions.eq("id", projectId)).uniqueResult();
    }

    @Override
    public JiraProject findByKey(String key) throws NoSuchEntityException{
        return (JiraProject) sessionFactory.getCurrentSession()
                .createCriteria(JiraProject.class).add(Restrictions.eq("key", key)).uniqueResult();
    }

    @Override
    public void delete(JiraProject jiraProject) throws NoSuchEntityException {
        JiraProject deleteJiraProject = findById(jiraProject.getId());

        if (deleteJiraProject != null) {
            sessionFactory.getCurrentSession().delete(jiraProject);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void delete(Long projectId) throws NoSuchEntityException {
        JiraProject jiraProject = findById(projectId);
        if (jiraProject != null) {
            sessionFactory.getCurrentSession().delete(jiraProject);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }
}
