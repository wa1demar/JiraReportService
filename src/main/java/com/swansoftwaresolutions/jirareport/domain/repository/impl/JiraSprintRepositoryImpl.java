package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraSprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Vitaliy Holovko
 */

@Repository
@Transactional
public class JiraSprintRepositoryImpl implements JiraSprintRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<JiraSprint> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("from JiraSprint s WHERE s.deleted != true");
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<JiraSprint> findByBoardId(Long boardId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from JiraSprint s where s.boardId = :boardId and s.deleted = false");
        query.setParameter("boardId", boardId);
        return query.list();
    }

    @Override
    public JiraSprint findById(Long id) {
        return (JiraSprint) sessionFactory.getCurrentSession()
                .createCriteria(JiraSprint.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public JiraSprint add(JiraSprint jiraSprint) {
        sessionFactory.getCurrentSession().save(jiraSprint);
        return jiraSprint;
    }

    @Override
    public JiraSprint addOrUpdate(JiraSprint jiraSprint) {
        JiraSprint sprint = new JiraSprint();
        try {
            sprint = findByNameAndBoardId(jiraSprint.getName(), jiraSprint.getBoardId());
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        if (sprint == null){
            sessionFactory.getCurrentSession().save(jiraSprint);
        } else {
            jiraSprint.setId(sprint.getId());
            try {
            sessionFactory.getCurrentSession().merge(jiraSprint);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return jiraSprint;
    }

    @Override
    public JiraSprint update(JiraSprint jiraSprint) throws NoSuchEntityException{
        sessionFactory.getCurrentSession().update(jiraSprint);
        if (findById(jiraSprint.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }

        return (JiraSprint) sessionFactory.getCurrentSession().merge(jiraSprint);
    }

    @Override
    public void deleteAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM JiraSprint js");
        query.executeUpdate();
    }

    @Override
    public void delete(Long id) throws NoSuchEntityException{
        JiraSprint jiraSprint = findById(id);
        if (jiraSprint != null) {
            sessionFactory.getCurrentSession().delete(jiraSprint);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public JiraSprint findByNameAndBoardId(String name, Long boardId) throws NoSuchEntityException {
        return (JiraSprint) sessionFactory.getCurrentSession()
                .createCriteria(JiraSprint.class).add(Restrictions.eq("name", name)).
                        add(Restrictions.eq("boardId", boardId)).uniqueResult();
    }

    @Override
    public void add(List<JiraSprint> sprints, JiraBoard board) {
        if (sprints == null || sprints.size() == 0) {
            return;
        }

        List<Long> ids = sprints.stream().map(r -> r.getSprintId()).collect(Collectors.toList());
        Query query = sessionFactory.getCurrentSession().createQuery("FROM JiraSprint js WHERE js.sprintId IN :ids and js.boardId = :boardId");
        query.setParameterList("ids", ids);
        query.setParameter("boardId", board.getId());
        List<JiraSprint> existed = query.list();

        Session session = sessionFactory.openSession();
        Map<Long, JiraSprint> sprintsMap = existed.stream().collect(Collectors.toMap(JiraSprint::getSprintId, b -> b));

        try {

            for (JiraSprint sprint : sprints) {
                JiraSprint existedSprint = sprintsMap.get(sprint.getSprintId());
                if (existedSprint != null) {
                    sprint.setBoardId(board.getId());
                    sprint.setId(existedSprint.getId());
                    session.update(sprint);
                } else {
                    sprint.setBoardId(board.getId());
                    session.save(sprint);
                }
            }

            session.flush();

        } finally {
            session.close();
        }
    }

    @Override
    public void setDeleted(Long sprintId) {
        Query query = sessionFactory.getCurrentSession().createQuery("UPDATE JiraSprint s set s.deleted = true where s.id = :id");
        query.setParameter("id", sprintId);
        query.executeUpdate();

        Query query1 = sessionFactory.getCurrentSession().createQuery("DELETE Sprint s where s.jiraSprint.id = :id");
        query1.setParameter("id", sprintId);
        query1.executeUpdate();
    }
}
