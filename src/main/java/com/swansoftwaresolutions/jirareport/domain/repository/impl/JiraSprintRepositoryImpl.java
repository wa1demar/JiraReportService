package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraSprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
public class JiraSprintRepositoryImpl implements JiraSprintRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<JiraSprint> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(JiraSprint.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<JiraSprint> findByBoardId(Long boardId) {
        return (List<JiraSprint>) sessionFactory.getCurrentSession()
                .createCriteria(JiraSprint.class).add(Restrictions.eq("boardId", boardId)).list();
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
}
