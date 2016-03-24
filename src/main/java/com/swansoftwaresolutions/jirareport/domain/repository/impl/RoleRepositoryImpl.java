package com.swansoftwaresolutions.jirareport.domain.repository.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Role;
import com.swansoftwaresolutions.jirareport.domain.enums.UserRole;
import com.swansoftwaresolutions.jirareport.domain.repository.RoleRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Vladimir Martynyuk
 */
@Repository
@Transactional
public class RoleRepositoryImpl implements RoleRepository {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Set<Role> findByName(UserRole roleManager) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Role r WHERE r.name = :name");
        query.setParameter("name", roleManager);
        return new HashSet<>(query.list());
    }
}
