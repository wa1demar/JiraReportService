package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraIssueRepository {

    JiraIssue add(JiraIssue jiraIssue);

    List<JiraIssue> findAll() throws NoSuchEntityException;

    List<JiraIssue> findBySprintId(long sprintId) throws NoSuchEntityException;

    JiraIssue findById(Long jiraIssueId) throws NoSuchEntityException;

    JiraIssue findByKey(String key) throws NoSuchEntityException;

    void delete(final JiraIssue jiraIssue) throws NoSuchEntityException;

    void delete(final Long JiraIssue) throws NoSuchEntityException;

    JiraIssue update(JiraIssue jiraIssue) throws NoSuchEntityException;

    List<JiraIssue> findBySprintIds(List<Long> ids);

    void saveAll(List<JiraIssue> list);
}
