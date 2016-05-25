package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MemberPositionDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.domain.repository.ProjectRepository;
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

    @Override
    public Project findById(Long projectId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Project p where p.id = :id");
        query.setParameter("id", projectId);
        return (Project) query.uniqueResult();
    }

    @Override
    public void delete(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete Project p where p.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Project update(Project project) {
        sessionFactory.getCurrentSession().update(project);
        return project;
    }

    @Override
    public void sortMembers(List<MemberPositionDto> users) {

    }
}
