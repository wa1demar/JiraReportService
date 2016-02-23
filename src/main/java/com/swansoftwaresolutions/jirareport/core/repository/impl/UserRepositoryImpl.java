package com.swansoftwaresolutions.jirareport.core.repository.impl;

import com.swansoftwaresolutions.jirareport.core.entity.User;
import com.swansoftwaresolutions.jirareport.core.repository.UserRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
