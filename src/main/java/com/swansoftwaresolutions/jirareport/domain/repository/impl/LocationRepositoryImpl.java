package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Location;
import com.swansoftwaresolutions.jirareport.domain.repository.LocationRepository;
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
public class LocationRepositoryImpl implements LocationRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Location> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Location l");
        return query.list();
    }

    @Override
    public Location add(Location location) {
        sessionFactory.getCurrentSession().save(location);
        return location;
    }

    @Override
    public Location delete(Long id) {
        Location location = findById(id);
        sessionFactory.getCurrentSession().delete(location);
        return location;
    }

    @Override
    public Location findById(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Location l WHERE l.id = :id");
        query.setParameter("id", id);
        return (Location) query.uniqueResult();
    }

    @Override
    public Location update(Location location) {
        sessionFactory.getCurrentSession().update(location);
        return location;
    }
}
