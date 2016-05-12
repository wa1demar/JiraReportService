package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Vladimir Martynyuk
 */
@Repository
@Transactional
public class SprintRepositoryImpl implements SprintRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Sprint add(Sprint sprint) {
        sessionFactory.getCurrentSession().save(sprint);
        return sprint;
    }

    @Override
    public Sprint update(Sprint sprint) {
        sessionFactory.getCurrentSession().merge(sprint);
        return sprint;
    }

    @Override
    public List<Sprint> findByReportId(long reportId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Sprint s WHERE s.report.id = :reportId ORDER BY s.startDate ASC");
        query.setParameter("reportId", reportId);

        return query.list();
    }

    @Override
    public Sprint delete(long sprintId) throws NoSuchEntityException {
        Sprint sprint = findById(sprintId);
        if (sprint != null) {
            sessionFactory.getCurrentSession().delete(sprint);
        } else {
            throw new NoSuchEntityException("Sprint not found");
        }
        return sprint;
    }

    @Override
    public Sprint findById(long sprintId) throws NoSuchEntityException {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Sprint s WHERE s.id = :sprintId");
        query.setParameter("sprintId", sprintId);
        Sprint sprint = (Sprint) query.uniqueResult();
        if (sprint  == null) {
            throw new NoSuchEntityException("Sprint not found");
        }
        return sprint;
    }

    @Override
    public void deleteByReportId(long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM Sprint s WHERE s.report.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void addAll(List<Sprint> sprints) {
        Session session = sessionFactory.openSession();

        try {
            for (Sprint sprint : sprints) {
                session.save(sprint);
            }
            session.flush();
        } finally {
            session.close();
        }

    }

    @Override
    public void addOrUpdate(List<JiraSprint> sprints, Report report) {
        if (sprints == null || sprints.size() == 0) {
            return;
        }

        Query query = sessionFactory.getCurrentSession().createQuery("FROM Sprint s where s.jiraSprint != null");
        List<Sprint> existed = query.list();

        Session session = sessionFactory.openSession();
//        Map<Long, Sprint> sprintsMap = existed.stream().collect(Collectors.toMap(id -> Sprint::getJiraSprint.getId, sprint -> sprint));

        Map<Long, Sprint> sprintsMap = new HashMap<>();
        for (Sprint s : existed) {
            sprintsMap.put(s.getJiraSprint().getSprintId(), s);
        }

        try {

            for (JiraSprint sprint : sprints) {
                Sprint existedSprint = sprintsMap.get(sprint.getSprintId());
                if (existedSprint == null) {
                    Sprint newSprint = new Sprint();
                    newSprint.setReport(report);
                    newSprint.setJiraSprint(sprint);
                    session.save(newSprint);
                }
            }

            session.flush();

        } finally {
            session.close();
        }

    }


}
