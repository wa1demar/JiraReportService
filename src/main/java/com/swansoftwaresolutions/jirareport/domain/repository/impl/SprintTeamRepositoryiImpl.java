package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.SprintTeam;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintTeamRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
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
public class SprintTeamRepositoryiImpl implements SprintTeamRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<SprintTeam> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(SprintTeam.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<SprintTeam> findByReportId(Long reportId) {
        return (List<SprintTeam>) sessionFactory.openSession()
                .createCriteria(SprintTeam.class).add(Restrictions.eq("reportId", reportId)).list();
    }

    @Override
    public List<SprintTeam> findByReportIdAndAgileSprintId(Long reportId, Long agileSprintId) {
        return (List<SprintTeam>) sessionFactory.openSession()
                .createCriteria(SprintTeam.class).add(Restrictions.eq("reportId", reportId)).add(Restrictions.eq("agileSprintId", agileSprintId)).uniqueResult();
    }

    @Override
    public SprintTeam findById(Long id) {
        return (SprintTeam) sessionFactory.openSession()
                .createCriteria(SprintTeam.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public SprintTeam findByAgileSprintId(Long agileSprintId) {
        return (SprintTeam) sessionFactory.openSession()
                .createCriteria(SprintTeam.class).add(Restrictions.eq("agileSprintId", agileSprintId)).uniqueResult();
    }

    @Override
    public SprintTeam add(SprintTeam sprintTeam) throws NoSuchEntityException{
        sessionFactory.getCurrentSession().save(sprintTeam);
        return sprintTeam;
    }

    @Override
    public SprintTeam update(SprintTeam sprintTeam) throws NoSuchEntityException{
        sessionFactory.getCurrentSession().update(sprintTeam);
        if (findById(sprintTeam.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }

        return (SprintTeam) sessionFactory.openSession().merge(sprintTeam);
    }

    @Override
    public void deleteAll() throws NoSuchEntityException{
        List<SprintTeam> sprintTeamList = findAll();
        if (sprintTeamList != null) {
            for (SprintTeam sprintTeam : sprintTeamList) {
                if (sprintTeam != null) {
                    sessionFactory.getCurrentSession().delete(sprintTeam);
                } else {
                    throw new NoSuchEntityException("Entity Not Found");
                }
            }
        } else {
            throw new NoSuchEntityException("Entities Not Found");
        }
    }

    @Override
    public void delete(SprintTeam sprintTeam) throws  NoSuchEntityException{
        SprintTeam deleteSprintTeam = findById(sprintTeam.getId());
        if (deleteSprintTeam != null) {
            sessionFactory.getCurrentSession().delete(sprintTeam);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void delete(Long id) throws NoSuchEntityException{
        SprintTeam deleteSprintTeam = findById(id);
        if (deleteSprintTeam != null) {
            sessionFactory.getCurrentSession().delete(deleteSprintTeam);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void deleteByReportId(Long reportId)  throws NoSuchEntityException{
        List<SprintTeam> sprintTeamList = findByReportId(reportId);
        if (sprintTeamList != null) {
            for (SprintTeam sprintTeam : sprintTeamList) {
                if (sprintTeam != null) {
                    sessionFactory.getCurrentSession().delete(sprintTeam);
                } else {
                    throw new NoSuchEntityException("Entity Not Found");
                }
            }
        } else {
            throw new NoSuchEntityException("Entities Not Found");
        }
    }
}
