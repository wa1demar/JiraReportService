package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import com.swansoftwaresolutions.jirareport.domain.repository.ResourceBordRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vladimir Martynyuk
 */
@Repository
@Transactional
public class ResourceBordRepositoryImpl implements ResourceBordRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public ResourceColumn add(ResourceColumn resourceColumn) {
        sessionFactory.getCurrentSession().save(resourceColumn);
        return resourceColumn;
    }

    @Override
    public ResourceColumn update(ResourceColumn resourceColumn) {
        sessionFactory.getCurrentSession().update(resourceColumn);
        return resourceColumn;
    }

    @Override
    public ResourceColumn findDefaultColumn() {
        Query query = sessionFactory.getCurrentSession().createQuery("from ResourceColumn c WHERE c.id = 1");
        return (ResourceColumn) query.uniqueResult();
    }

    @Override
    public List<ResourceColumn> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("from ResourceColumn c");
        return query.list();
    }

    @Override
    public List<JiraUser> findUsersByColumnId(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from JiraUser u where u.column.id = :id");
        query.setParameter("id", id);
        return query.list();
    }

    @Override
    public void removeColumn(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from ResourceColumn c where c.id = :id").setParameter("id", id);
        ResourceColumn column = (ResourceColumn) query.uniqueResult();
        sessionFactory.getCurrentSession().delete(column);
    }

    @Override
    public void moveUsersToDefaultColumn(List<JiraUser> users) {
        List<String> logins = users.stream().map(u -> u.getLogin()).collect(Collectors.toList());
        if (logins.size() > 0) {
            Query query = sessionFactory.getCurrentSession().createQuery("update JiraUser  u set u.column = (from ResourceColumn c where c.id = 1) where u.login in (:logins)").setParameterList("logins", logins);
            query.executeUpdate();
        }
    }
}
