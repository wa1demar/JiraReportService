package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */
public interface JiraUserRepository {

    JiraUser add(JiraUser project);

    void delete(final JiraUser jiraUser) throws NoSuchEntityException;

    void delete(final Long jiraUserId) throws NoSuchEntityException;

    JiraUser findById(long id);

    JiraUser update(JiraUser user) throws NoSuchEntityException;

    List<JiraUser> findAll();
}
