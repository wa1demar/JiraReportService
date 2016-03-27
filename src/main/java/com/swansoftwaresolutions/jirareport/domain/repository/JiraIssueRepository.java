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

    JiraIssue findById(Long jiraIssueId);

    void delete(final JiraIssue jiraIssue) throws NoSuchEntityException;

    void delete(final Long JiraIssue) throws NoSuchEntityException;

    JiraIssue update(JiraIssue jiraIssue) throws NoSuchEntityException;
}
