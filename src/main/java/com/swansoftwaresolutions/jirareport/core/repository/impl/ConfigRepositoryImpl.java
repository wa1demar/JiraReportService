package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Config;
import com.swansoftwaresolutions.jirareport.core.repository.ConfigRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Repository
@Transactional
public class ConfigRepositoryImpl implements ConfigRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Config add(Config config) {
            sessionFactory.getCurrentSession().persist(config);
            return config;
    }

    @Override
    public List<Config> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Config.class).
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public Config findById(Long id) {
        return (Config) sessionFactory.openSession()
                .createCriteria(Config.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public void delete(Config config) throws NoSuchEntityException {
        Config deleteConfig = findById(config.getId());
        if (deleteConfig != null) {
            sessionFactory.getCurrentSession().delete(config);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void delete(Long configId) throws NoSuchEntityException {
        Config config = findById(configId);
        if (config != null) {
            sessionFactory.getCurrentSession().delete(config);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public Config update(Config config) throws NoSuchEntityException {
        if (config.getId() == null || findById(config.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (Config) sessionFactory.openSession().merge(config);
    }

}
