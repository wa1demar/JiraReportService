package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraBoardRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Vitaliy Holovko
 */

@Repository
@Transactional
public class JiraBoardRepositoryImpl implements JiraBoardRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<JiraBoard> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM JiraBoard jb ORDER BY jb.name ASC");
        return query.list();
    }

    @Override
    public JiraBoard add(JiraBoard jiraBoard) {
        sessionFactory.getCurrentSession().persist(jiraBoard);
        return jiraBoard;
    }

    @Override
    public JiraBoard findById(Long jiraBoardId) {
        return (JiraBoard) sessionFactory.getCurrentSession()
                .createCriteria(JiraBoard.class).add(Restrictions.eq("id", jiraBoardId)).uniqueResult();

    }

    @Override
    public void delete(JiraBoard jiraBoard) throws NoSuchEntityException {
        JiraBoard deleteJiraBoard = findById(jiraBoard.getId());

        if (deleteJiraBoard != null) {
            sessionFactory.getCurrentSession().delete(jiraBoard);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }

    }

    @Override
    public void delete(Long jiraBoardId) throws NoSuchEntityException {
        JiraBoard jiraBoard = findById(jiraBoardId);
        if (jiraBoard != null) {
            sessionFactory.getCurrentSession().delete(jiraBoard);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public JiraBoard update(JiraBoard jiraBoard) throws NoSuchEntityException {
        if (findById(jiraBoard.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (JiraBoard) sessionFactory.getCurrentSession().merge(jiraBoard);
    }

    @Override
    public List<JiraBoard> findByIds(List<Long> ids) {
        if (ids != null && ids.size() > 0) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM JiraBoard jb WHERE jb.id IN :ids ORDER BY jb.name ASC");
        query.setParameterList("ids", ids);
        return query.list();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void add(List<JiraBoard> jiraBoards, String key) {
        if (jiraBoards == null || jiraBoards.size() == 0) {
            return;
        }
        List<Long> ids = jiraBoards.stream().map(r -> r.getBoardId()).collect(Collectors.toList());
        Query query = sessionFactory.getCurrentSession().createQuery("FROM JiraBoard jb WHERE jb.boardId IN :ids");
        query.setParameterList("ids", ids);
        List<JiraBoard> existed = query.list();
        Map<Long, JiraBoard> boards = existed.stream().collect(Collectors.toMap(JiraBoard::getBoardId, b -> b));


        Session session = sessionFactory.openSession();

        try {

            for (JiraBoard board : jiraBoards) {
                JiraBoard existedBoard = boards.get(board.getBoardId());
                if (existedBoard == null) {
                    board.setProjectKey(key);
                    session.save(board);
                } else {
                    existedBoard.setName(board.getName());
                    existedBoard.setProjectKey(key);
                    existedBoard.setType(board.getType());

                    session.update(existedBoard);
                }
            }
            session.flush();

        } finally {

            session.close();
        }
    }

    @Override
    public List<JiraBoard> findAllWithReports() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM JiraBoard jb WHERE jb. IN :ids ORDER BY jb.name ASC");
        return null;
    }
}
