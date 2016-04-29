package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MemberDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraGroup;
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

    JiraUser findByLogin(String login) throws NoSuchEntityException;

    JiraUser merge(JiraUser user) throws NoSuchEntityException;

    JiraUser update(JiraUser user) throws NoSuchEntityException;

    List<JiraUser> findAll();

    List<JiraUser> findByLogins(String[] admins);

    void saveAll(List<JiraUser> jiraUsers, JiraGroup group);

    List<JiraUser> findByGroups(String[] groups);

    JiraUser deleteUserFromColumn(String login) throws NoSuchEntityException;

    JiraUser updateJiraUserInfo(String login, MemberDto memberDto) throws NoSuchEntityException;

    void updateAll(List<JiraUser> users);
}
