package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUsersReferences;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface JiraUsersReferencesRepository {
    JiraUsersReferences add(JiraUsersReferences jiraUsersReferences);

    void deleteByAssignmentType(String login, Long fromAssignmentTypeId);

    void deleteByAssignmentType(String login);

    void deleteByProject(String login, Long projectId);

    List<Project> findByUserAndAssignmentType(String login, Long fromAssignmentTypeId);

}
