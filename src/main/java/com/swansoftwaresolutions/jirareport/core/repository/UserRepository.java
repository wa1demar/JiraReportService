package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.User;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;

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

    void delete(final Long projectId) throws NoSuchEntityException;

    User update(User project) throws NoSuchEntityException;

}
