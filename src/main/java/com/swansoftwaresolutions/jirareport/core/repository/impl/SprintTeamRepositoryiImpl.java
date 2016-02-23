package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.SprintTeam;
import com.swansoftwaresolutions.jirareport.core.repository.SprintTeamRepository;
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
public class SprintTeamRepositoryiImpl implements SprintTeamRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<SprintTeam> getAllSprintTeams() {
        return sessionFactory.getCurrentSession().createCriteria(SprintTeam.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<SprintTeam> getSprintTeamsByReportId(Long reportId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintTeam.findByReportId");
        query.setParameter("reportId", reportId);

        @SuppressWarnings("unchecked")
        List<SprintTeam> sprintTeams = (List<SprintTeam>) query.list();
        return sprintTeams;
    }

    @Override
    public List<SprintTeam> getSprintTeamByReportIdAndAgileSprintId(Long reportId, Long agileSprintId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintTeam.findByReportIdAndAgileSprintId");
        query.setParameter("reportId", reportId);
        query.setParameter("agileSprintId", agileSprintId);

        @SuppressWarnings("unchecked")
        List<SprintTeam> sprintTeams = (List<SprintTeam>) query.list();
        return sprintTeams;
    }

    @Override
    public SprintTeam getSprintTeamById(Long id) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintIssue.findById");
        query.setParameter("id", id);

        @SuppressWarnings("unchecked")
        SprintTeam sprintTeam = (SprintTeam) query.uniqueResult();
        return sprintTeam;
    }

    @Override
    public SprintTeam getSprintTeamByAgileSprintId(Long agileSprintId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintIssue.findByAgileSprintId");
        query.setParameter("agileSprintId", agileSprintId);

        @SuppressWarnings("unchecked")
        SprintTeam sprintTeam = (SprintTeam) query.uniqueResult();
        return sprintTeam;
    }

    @Override
    public void createSprintTeam(SprintTeam sprintTeam) {
        sessionFactory.getCurrentSession().save(sprintTeam);

    }

    @Override
    public void updateSprintTeam(SprintTeam sprintTeam) {
        sessionFactory.getCurrentSession().update(sprintTeam);
    }

    @Override
    public void deleteAllSprintTeam() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintTeam.deleteAll");
        query.executeUpdate();
    }

    @Override
    public void deleteSprintTeam(SprintTeam sprintTeam) {
        sessionFactory.getCurrentSession().delete(sprintTeam);
    }

    @Override
    public void deleteSprintTeam(Long id) {
        sessionFactory.getCurrentSession().delete(id);
    }

    @Override
    public void deleteSprintTeamsByReportId(Long reportId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("SprintTeam.deleteByReportId");
        query.setParameter("reportId", reportId);
        query.executeUpdate();
    }
}
