package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;
import java.util.Set;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */
public interface JiraUserRepository {

    JiraUser add(JiraUser project);

    void delete(final JiraUser jiraUser) throws NoSuchEntityException;

    JiraUser findByLogin(String login) throws NoSuchEntityException;

    JiraUser update(JiraUser user) throws NoSuchEntityException;

    List<JiraUser> findAll();

    List<JiraUser> findByLogins(String[] admins);
}
