package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUsersReferences;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUsersReferencesRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public void deleteByAssignmentType(String login, Long fromAssignmentTypeId) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from JiraUsersReferences r where r.user.login = :login and r.column.id = :columnId");
        query.setParameter("login", login);
        query.setParameter("columnId", fromAssignmentTypeId);
        query.executeUpdate();
    }

    @Override
    public void deleteByAssignmentType(String login) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from JiraUsersReferences r where r.user.login = :login");
        query.setParameter("login", login);
        query.executeUpdate();
    }

    @Override
    public void deleteByProject(String login, Long projectId) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from JiraUsersReferences r where r.user.login = :login and r.project.id = :id");
        query.setParameter("login", login);
        query.setParameter("id", projectId);
        query.executeUpdate();
    }

    @Override
    public List<Project> findByUserAndAssignmentType(String login, Long fromAssignmentTypeId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select r.project from JiraUsersReferences r where r.user.login = :login and r.column.id = :id");
        query.setParameter("login", login);
        query.setParameter("id", fromAssignmentTypeId);
        return query.list();
    }

    @Override
    public List<JiraUsersReferences> findByProjectId(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from JiraUsersReferences r where r.project.id = :id");
        query.setParameter("id", id);
        return query.list();
    }

    @Override
    public List<JiraUser> findUsersByProjectId(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("select r.user from JiraUsersReferences r where r.project.id = :id");
        query.setParameter("id", id);
        return query.list();
    }

    @Override
    public List<Project> findByUserAndProjectId(String login, Long fromProjectId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select r from JiraUsersReferences r where r.project.id = :id and r.user.login = :login");
        query.setParameter("id", fromProjectId);
        query.setParameter("login", login);
        return query.list();
    }

}
