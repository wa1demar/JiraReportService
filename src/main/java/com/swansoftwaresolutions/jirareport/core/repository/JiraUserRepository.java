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

    void delete(final JiraUser jiraUser) throws NoSuchEntityException;

    void delete(final Long jiraUserId) throws NoSuchEntityException;

    JiraUser findById(long id) throws NoSuchEntityException;

    JiraUser findByName(String name) throws NoSuchEntityException;

    JiraUser update(JiraUser user) throws NoSuchEntityException;

    List<JiraUser> findAll();
}
