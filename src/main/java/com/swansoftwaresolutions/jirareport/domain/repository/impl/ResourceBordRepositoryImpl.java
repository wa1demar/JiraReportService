package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import com.swansoftwaresolutions.jirareport.domain.repository.ResourceBordRepository;
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
    public List<ResourceColumn> findDefaultColumn() {
        Query query = sessionFactory.getCurrentSession().createQuery("from ResourceColumn c WHERE c.id = 1");
        return query.list();
    }
}
