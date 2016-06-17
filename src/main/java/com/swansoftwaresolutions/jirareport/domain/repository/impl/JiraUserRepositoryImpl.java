package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MemberDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MemberPositionDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraGroup;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.Technology;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */

@Repository
@Transactional
public class JiraUserRepositoryImpl implements JiraUserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public JiraUser add(JiraUser jiraUser) {
        sessionFactory.getCurrentSession().save(jiraUser);
        return jiraUser;
    }

    @Override
    public JiraUser merge(JiraUser user) throws NoSuchEntityException {
        return (JiraUser) sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    public JiraUser update(JiraUser user) throws NoSuchEntityException {

        Set<Technology> technologies = new HashSet<>(user.getTechnologies());
        for (Technology technology : technologies) {
            Set<JiraUser> users = new HashSet<>(technology.getUsers());
            users.add(user);
            technology.setUsers(new ArrayList<>(users));
        }
        user.setTechnologies(new ArrayList<>(technologies));

        sessionFactory.getCurrentSession().update(user);
        return user;
    }

    @Override
    public JiraUser findByLogin(String login) throws NoSuchEntityException {
        Query query = sessionFactory.getCurrentSession().createQuery("from JiraUser u WHERE u.login = :login");
        query.setParameter("login", login);
        return (JiraUser) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<JiraUser> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM JiraUser u");
        return query.list();
    }

    @Override
    public void delete(JiraUser jiraUser) throws NoSuchEntityException {
        sessionFactory.getCurrentSession().delete(jiraUser);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<JiraUser> findByLogins(String[] admins) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM JiraUser u WHERE u.login in :admins");
        query.setParameterList("admins", admins);
        return query.list();
    }

    @Override
    public void saveAll(List<JiraUser> jiraUsers, JiraGroup group) {
        JiraUser existed = null;
        for (JiraUser user : jiraUsers) {
            try {
                existed = findByLogin(user.getLogin());
            } catch (NoSuchEntityException ex) {
                // TODO: new user
            }

            if (existed == null) {
                user.setGroups(new ArrayList<JiraGroup>() {{
                    add(group);
                }});
                add(user);
            } else {
                existed.setEmail(user.getEmail());
                existed.setFullName(user.getFullName());
                existed.setAvatar(user.getAvatar());

                Set<JiraGroup> groups = new HashSet<>(existed.getGroups());
                List<JiraUser> existedusers = group.getUsers();
                existedusers.add(existed);
                group.setUsers(existedusers);
                groups.add(group);

                existed.setGroups(new ArrayList<>(groups));

                sessionFactory.getCurrentSession().update(existed);

            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<JiraUser> findByGroups(String[] groups) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT u FROM JiraUser u INNER JOIN u.groups g WHERE g.name IN :groups");
        query.setParameterList("groups", Arrays.asList(groups));

        return query.list();
    }

    @Override
    public JiraUser deleteUserFromColumn(String login) throws NoSuchEntityException {
        Query query = sessionFactory.getCurrentSession().createQuery("delete JiraUsersReferences r where r.user.login = :login");
        query.setParameter("login", login);
        query.executeUpdate();

        return findByLogin(login);

    }

    @Override
    public JiraUser updateJiraUserInfo(String login, MemberDto memberDto) throws NoSuchEntityException {
        Query query = sessionFactory.getCurrentSession().createQuery("update JiraUser u set u.description = :description, u.notShowCircles = :notShowCircles, " +
                "u.location = (from Location l where l.id = :locationId), " +
                "u.position= (from Position p where p.id = :positionId) where u.login = :login") ;
        query.setParameter("login", login);
        query.setParameter("description", memberDto.getDescription());
        query.setParameter("locationId", memberDto.getLocationId());
        query.setParameter("positionId", memberDto.getEngineerLevel());
        query.setParameter("notShowCircles", memberDto.isNotShowCircles());
        query.executeUpdate();
        return findByLogin(login);
    }

    @Override
    public void updateAll(List<JiraUser> users) {

        for (JiraUser user : users) {
            sessionFactory.getCurrentSession().update(user);
        }


    }

    @Override
    public void sortMembers(List<MemberPositionDto> users) {
        Session session = sessionFactory.openSession();
        for (MemberPositionDto dto : users) {
            try {
                Query query = session.createQuery("update JiraUser u set u.resourceOrder = :position where u.login = :login");
                query.setParameter("position", dto.getIndex());
                query.setParameter("login", dto.getLogin());
                query.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        session.flush();
        session.close();

    }

    @Override
    public List<JiraUser> findFromBench() {
        Query query = sessionFactory.getCurrentSession().createQuery("select u from JiraUser u join u.userReferences r join u.technologies t where r.column.id = 1");
        return query.list();
    }

}
