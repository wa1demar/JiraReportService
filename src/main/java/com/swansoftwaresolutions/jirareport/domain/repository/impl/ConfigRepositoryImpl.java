package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Config;
import com.swansoftwaresolutions.jirareport.domain.entity.builder.ConfigBuilder;
import com.swansoftwaresolutions.jirareport.domain.repository.ConfigRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vitaliy Holovko
 */

@Repository
@Transactional
public class ConfigRepositoryImpl implements ConfigRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Config findFirst() {
        Config config = (Config) sessionFactory.getCurrentSession().createCriteria(Config.class).
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();

        if (config != null) {
            return config;
        } else {
            return update(new ConfigBuilder()
                    .agileDoneName("")
                    .jiraUser("")
                    .jiraPassword("")
                    .bugName("")
                    .jiraDevGroupName("")
                    .nonWorkingDays("")
                    .build());
        }

    }

    @Override
    public Config findById(Long id) {
        return (Config) sessionFactory.getCurrentSession()
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
    public Config update(Config config){
        sessionFactory.getCurrentSession().saveOrUpdate(config);
        return config;
    }

}
