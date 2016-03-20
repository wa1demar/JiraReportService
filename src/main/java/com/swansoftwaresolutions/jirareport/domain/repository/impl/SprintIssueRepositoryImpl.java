package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.SprintIssue;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintIssueRepository;
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
public class SprintIssueRepositoryImpl implements SprintIssueRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<SprintIssue> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(SprintIssue.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<SprintIssue> findBySprintId(Long sprintId){
        return (List<SprintIssue>) sessionFactory.getCurrentSession()
                .createCriteria(SprintIssue.class).add(Restrictions.eq("reportId", sprintId)).list();
    }

    @Override
    public List<SprintIssue> findBySprintIdAndAssignee(Long sprintId, String assignee) {
        return (List<SprintIssue>) sessionFactory.getCurrentSession()
                .createCriteria(SprintIssue.class).add(Restrictions.eq("sprintId", sprintId)).add(Restrictions.eq("assignee", assignee)).list();
    }

    @Override
    public List<SprintIssue> findBySprintIdAndAssigneeAndIssueDate(Long sprintId, String assignee, String issueDate) {
        return (List<SprintIssue>) sessionFactory.getCurrentSession()
                .createCriteria(SprintIssue.class).
                        add(Restrictions.eq("sprintId", sprintId)).
                        add(Restrictions.eq("assignee", assignee)).
                        add(Restrictions.eq("issueDate", issueDate)).list();
    }

    @Override
    public SprintIssue findById(Long id) {
        return (SprintIssue) sessionFactory.getCurrentSession()
                .createCriteria(SprintIssue.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public SprintIssue add(SprintIssue sprintIssue) {
        sessionFactory.getCurrentSession().save(sprintIssue);
        return sprintIssue;
    }

    @Override
    public SprintIssue update(SprintIssue sprintIssue) throws NoSuchEntityException {
        sessionFactory.getCurrentSession().update(sprintIssue);
        if (findById(sprintIssue.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }

        return (SprintIssue) sessionFactory.getCurrentSession().merge(sprintIssue);
    }

    @Override
    public void deleteAll() throws NoSuchEntityException{
        List<SprintIssue> sprintList = findAll();
        if (sprintList != null) {
            for (SprintIssue sprint : sprintList) {
                if (sprint != null) {
                    sessionFactory.getCurrentSession().delete(sprint);
                } else {
                    throw new NoSuchEntityException("Entity Not Found");
                }
            }
        } else {
            throw new NoSuchEntityException("Entities Not Found");
        }
    }

    @Override
    public void delete(SprintIssue sprintIssue) throws NoSuchEntityException {
        SprintIssue deleteSprintIssue = findById(sprintIssue.getId());
        if (deleteSprintIssue != null) {
            sessionFactory.getCurrentSession().delete(sprintIssue);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void delete(Long id) throws NoSuchEntityException{
        SprintIssue deleteSprintIssue = findById(id);
        if (deleteSprintIssue != null) {
            sessionFactory.getCurrentSession().delete(deleteSprintIssue);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public void deleteBySprintId(Long sprintId)throws NoSuchEntityException{
        List<SprintIssue> sprintIssueList = findBySprintId(sprintId);
        if (sprintIssueList != null) {
            for (SprintIssue sprintIssue : sprintIssueList) {
                if (sprintIssue != null) {
                    sessionFactory.getCurrentSession().delete(sprintIssue);
                } else {
                    throw new NoSuchEntityException("Entity Not Found");
                }
            }
        } else {
            throw new NoSuchEntityException("Entities Not Found");
        }
    }

    @Override
    public void deleteBySprintIdAndAssignee(Long sprintId, String assignee) throws NoSuchEntityException{
        List<SprintIssue> sprintIssueList = findBySprintIdAndAssignee(sprintId, assignee);
        if (sprintIssueList != null) {
            for (SprintIssue sprintIssue : sprintIssueList) {
                if (sprintIssue != null) {
                    sessionFactory.getCurrentSession().delete(sprintIssue);
                } else {
                    throw new NoSuchEntityException("Entity Not Found");
                }
            }
        } else {
            throw new NoSuchEntityException("Entities Not Found");
        }
    }
}
