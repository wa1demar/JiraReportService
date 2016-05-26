package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MemberPositionDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnPriority;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.domain.repository.ProjectRepository;
import org.hibernate.Query;
import org.hibernate.Session;
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
    public void sortMembers(List<MemberPositionDto> users, Long projectId) {

        if (users != null && users.size() > 0) {
            Session session = sessionFactory.openSession();
            try {
                for (MemberPositionDto positionDto : users) {
                    Query query = session.createQuery("update JiraUsersReferences r set r.positionInColumn = :priority where r.user.login = :login and r.project.id = :id")
                            .setParameter("id", projectId)
                            .setParameter("priority", positionDto.getIndex())
                            .setParameter("login", positionDto.getLogin());
                    query.executeUpdate();
                }

                session.flush();

            } finally {
                session.close();
            }
        }
    }

    @Override
    public void sort(List<ResourceColumnPriority> projectPositionDto) {
        if (projectPositionDto != null && projectPositionDto.size() > 0) {
            Session session = sessionFactory.openSession();
            try {
                for (ResourceColumnPriority positionDto : projectPositionDto) {
                    Query query = session.createQuery("update Project  p set p.sortPosition = :priority where p.id = :id")
                            .setParameter("id", positionDto.getColumnId())
                            .setParameter("priority", positionDto.getColumnPriority());
                    query.executeUpdate();
                }

                session.flush();

            } finally {
                session.close();
            }

        }
    }
}
