package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.JiraUser;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */
public interface JiraUserRepository {

    JiraUser add(JiraUser project);

    List<JiraUser> getAllUsers();

    void deleteUser(final JiraUser jiraUser);

    void deleteUser(final Long jiraUserId);
}
