package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.User;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface UserRepository {
    User findByUsername(String username);

    User add(User project);

    List<User> findAll();

    User findById(Long projectId);

    void delete(final User project) throws NoSuchEntityException;

    User delete(final Long projectId) throws NoSuchEntityException;

    User update(User user) throws NoSuchEntityException;

}
