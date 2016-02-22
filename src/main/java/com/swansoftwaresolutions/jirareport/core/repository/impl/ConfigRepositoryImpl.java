package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Config;
import com.swansoftwaresolutions.jirareport.core.repository.ConfigRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
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
    public List<Config> getConfigData() {
        return sessionFactory.getCurrentSession().createCriteria(Config.class).
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public void createConfig(Config config) {
        sessionFactory.getCurrentSession().save(config);
    }

    @Override
    public void updateConfig(Config config) {
        sessionFactory.getCurrentSession().update(config);
    }

    @Override
    public void deleteConfig(Config config) {
        sessionFactory.getCurrentSession().delete(config);
    }

    @Override
    public void deleteConfig(Long id) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("Config.deleteById");
        query.setParameter("id", id);

        query.executeUpdate();

    }
}
