package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintDeveloperRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Repository
@Transactional
public class SprintDeveloperRepositoryImpl implements SprintDeveloperRepository {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<SprintDeveloper> findBySprintId(long sprintId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM SprintDeveloper dev WHERE dev.sprint.id = :sprintId ORDER BY dev.id ASC");
        query.setParameter("sprintId", sprintId);
        return query.list();
    }

    @Override
    public SprintDeveloper add(SprintDeveloper developer) {
        sessionFactory.getCurrentSession().save(developer);
        return developer;
    }

    @Override
    public void delete(List<Long> ids, Long sprintId) {
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM SprintDeveloper dev WHERE dev.sprint.id = :sprintId and dev.id NOT IN :ids");
        query.setParameter("sprintId", sprintId);
        query.setParameterList("ids", ids.size() > 0 ? ids : new ArrayList<Long>(){{ add(0L);}});
        query.executeUpdate();
    }

    @Override
    public void deleteBySprintId(Long sprintId) {
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM SprintDeveloper dev WHERE dev.sprint.id = :sprintId");
        query.setParameter("sprintId", sprintId);
        query.executeUpdate();
    }

    @Override
    public List<SprintDeveloper> findByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return new ArrayList<>();
        }
        Query query = sessionFactory.getCurrentSession().createQuery("FROM SprintDeveloper dev WHERE dev.sprint.id in :ids ORDER BY dev.id ASC");
        query.setParameterList("ids", ids);
        return query.list();
    }
}
