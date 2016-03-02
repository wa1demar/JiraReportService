package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.User;
import com.swansoftwaresolutions.jirareport.core.repository.UserRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findByUsername(String username) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("User.findByUsername");
        query.setParameter("username", username);

        return (User) query.uniqueResult();
    }

    @Override
    public User add(User user) {
        sessionFactory.getCurrentSession().persist(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public User findById(Long projectId) {
        return (User) sessionFactory.openSession()
                .createCriteria(User.class).add(Restrictions.eq("id", projectId)).uniqueResult();
    }

    @Override
    public void delete(User user) throws NoSuchEntityException {
        User deleteUser = findById(user.getId());

        if (deleteUser != null) {
            sessionFactory.getCurrentSession().delete(user);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }

    }

    @Override
    public void delete(Long userId) throws NoSuchEntityException {
        User user = findById(userId);
        if (user != null) {
            sessionFactory.getCurrentSession().delete(user);
        } else {
            throw new NoSuchEntityException("Entity Not Found");
        }
    }

    @Override
    public User update(User user) throws NoSuchEntityException {
        if (findById(user.getId()) == null) {
            throw new NoSuchEntityException("Entity not found");
        }
        return (User) sessionFactory.openSession().merge(user);
    }
}
