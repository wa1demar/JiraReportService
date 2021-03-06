package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.Position;
import com.swansoftwaresolutions.jirareport.domain.repository.PositionRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Repository
@Transactional
public class PositionRepositoryImpl implements PositionRepository {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Position> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Position p");
        return query.list();
    }

    @Override
    public Position add(Position position) {
        sessionFactory.getCurrentSession().save(position);
        return position;
    }

    @Override
    public Position delete(Long id) {
        Position position = findById(id);
        List<JiraUser> users = position.getUsers();
        for (JiraUser u : users) {
            u.setPosition(null);
            Query query = sessionFactory.getCurrentSession().createQuery("update JiraUser u set u.position = null where u.login = :login");
            query.setParameter("login", u.getLogin());
            query.executeUpdate();
        }

        Query query = sessionFactory.getCurrentSession().createQuery("delete Position p where p.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        return position;
    }

    @Override
    public Position update(Position position) {
        sessionFactory.getCurrentSession().update(position);
        return position;
    }

    @Override
    public Position findById(Long level) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Position p where p.id = :id");
        query.setParameter("id", level);
        return (Position) query.uniqueResult();
    }

}
