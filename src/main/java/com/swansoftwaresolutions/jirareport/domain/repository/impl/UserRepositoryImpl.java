package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.User;
import com.swansoftwaresolutions.jirareport.domain.repository.UserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
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
        return (User) sessionFactory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("username", username))
                .uniqueResult();
    }

    @Override
    public User add(User user) {
        sessionFactory.getCurrentSession().persist(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class)
                .list();
    }

    @Override
    public User findById(Long id) {
        return (User) sessionFactory.openSession()
                .createCriteria(User.class).add(Restrictions.eq("id", id)).uniqueResult();
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
