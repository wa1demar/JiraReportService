package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnPriority;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import com.swansoftwaresolutions.jirareport.domain.repository.ResourceBordRepository;
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
        if (resourceColumn.getId().equals(1L)) {
            resourceColumn.setFixed(true);
        }
        sessionFactory.getCurrentSession().update(resourceColumn);
        return resourceColumn;
    }

    @Override
    public ResourceColumn findDefaultColumn() {
        Query query = sessionFactory.getCurrentSession().createQuery("from ResourceColumn c WHERE c.id = 1");
        return (ResourceColumn) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ResourceColumn> findAll(boolean sorted) {
        Query query = null;
        if (sorted) {
            query = sessionFactory.getCurrentSession().createQuery("from ResourceColumn c order by c.sortPosition ASC");
        } else {
            query = sessionFactory.getCurrentSession().createQuery("from ResourceColumn c order by c.priority ASC");
        }
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<JiraUser> findUsersByColumnId(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("select u from JiraUser u join u.userReferences r where r.column.id = :id");
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
    public void updatePriorities(ResourceColumnPriority[] columnPriorities) {

        if (columnPriorities != null && columnPriorities.length > 0) {
            Session session = sessionFactory.openSession();
            try {
                for (int i = 0; i < columnPriorities.length; i++) {
                    Query query = session.createQuery("update ResourceColumn  c set c.priority = :priority where c.id = :id")
                            .setParameter("id", columnPriorities[i].getColumnId())
                            .setParameter("priority", columnPriorities[i].getColumnPriority());
                    query.executeUpdate();
                }

                session.flush();

            } finally {
                session.close();
            }

        }

    }

    @Override
    public ResourceColumn findById(Long toAssignmentTypeId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from ResourceColumn c where c.id = :id");
        query.setParameter("id", toAssignmentTypeId);
        return (ResourceColumn) query.uniqueResult();
    }

    @Override
    public void sort(ResourceColumnPriority[] columnPriorities) {
        if (columnPriorities != null && columnPriorities.length > 0) {
            Session session = sessionFactory.openSession();
            try {
                for (int i = 0; i < columnPriorities.length; i++) {
                    Query query = session.createQuery("update ResourceColumn  c set c.sortPosition = :priority where c.id = :id")
                            .setParameter("id", columnPriorities[i].getColumnId())
                            .setParameter("priority", columnPriorities[i].getColumnPriority());
                    query.executeUpdate();
                }

                session.flush();

            } finally {
                session.close();
            }

        }
    }
}
