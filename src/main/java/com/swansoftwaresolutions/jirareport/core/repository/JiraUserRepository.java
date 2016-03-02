package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */
public interface JiraUserRepository {

    JiraUser add(JiraUser project);

    List<JiraUser> findAll();

    JiraUser findById(Long projectId);

    void delete(final JiraUser project) throws NoSuchEntityException;

    void delete(final Long projectId) throws NoSuchEntityException;

    JiraUser update(JiraUser project) throws NoSuchEntityException;
}
