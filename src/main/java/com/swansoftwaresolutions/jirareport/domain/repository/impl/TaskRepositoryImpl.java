package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Task;
import com.swansoftwaresolutions.jirareport.domain.enums.TaskStatus;
import com.swansoftwaresolutions.jirareport.domain.repository.TaskRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Repository
@Transactional
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Task> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Task s ORDER BY s.order ASC");
        return query.list();
    }

    @Override
    public Task findByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Task s WHERE s.name = :name");
        query.setParameter("name", name);
        return (Task) query.uniqueResult();
    }

    @Override
    public void setStarted(String name) {
        Task task = findByName(name);
        task.setStatus(TaskStatus.IN_PROGRESS);
        sessionFactory.getCurrentSession().update(task);
    }

    @Override
    public Task setStopped(String name) {
        Task task = findByName(name);
        task.setStatus(TaskStatus.DONE);
        task.setLastUpdate(new Date());
        sessionFactory.getCurrentSession().update(task);

        return task;
    }

    @Override
    public void setErrored(String name) {
        Task task = findByName(name);
        task.setStatus(TaskStatus.ERROR);
        sessionFactory.getCurrentSession().update(task);
    }
}
