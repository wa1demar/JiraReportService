package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Technology;
import com.swansoftwaresolutions.jirareport.domain.repository.TechnologyRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Repository
@Transactional
public class TechnologyRepositoryImpl implements TechnologyRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Technology> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Technology t ORDER BY t.name");
        return query.list();
    }

    @Override
    public Technology add(Technology technology) {
        sessionFactory.getCurrentSession().save(technology);
        return technology;
    }

    @Override
    public Technology delete(Long id) {
        Technology technology = findById(id);
        sessionFactory.getCurrentSession().delete(technology);
        return technology;
    }

    @Override
    public Technology findById(Long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Technology t WHERE t.id = :id");
        query.setParameter("id", id);
        return (Technology) query.uniqueResult();
    }

    @Override
    public Technology update(Technology technology) {
        sessionFactory.getCurrentSession().update(technology);
        return technology;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Technology> findAllByIds(Long[] technologies) {
        if (technologies == null || technologies.length == 0) {
            return new ArrayList<>();
        }
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Technology t WHERE t.id in (:ids)");
        query.setParameterList("ids", technologies);
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public  List<Technology> findAllByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return new ArrayList<>();
        }
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Technology t WHERE t.id in (:ids)");
        query.setParameterList("ids", ids);
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Technology> findBench() {
        Query query = sessionFactory.getCurrentSession().createQuery("select t FROM Technology t join t.users u join u.userReferences r where r.column.id = 1");
        return query.list();
    }
}
