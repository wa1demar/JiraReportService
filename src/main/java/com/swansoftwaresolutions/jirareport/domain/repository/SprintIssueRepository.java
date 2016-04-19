package com.swansoftwaresolutions.jirareport.domain.repository;


import com.swansoftwaresolutions.jirareport.domain.entity.SprintIssue;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

public interface SprintIssueRepository {

    List<SprintIssue> findAll();

    List<SprintIssue> findBySprintId(final Long sprintId);

    List<SprintIssue> findBySprintIdAndAssignee(final Long sprintId, final String assignee);

    SprintIssue findById(final Long id);

    SprintIssue add(final SprintIssue sprintIssue);

    SprintIssue update(final SprintIssue sprintIssue) throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;

    void delete(SprintIssue sprint) throws NoSuchEntityException;

    void delete(Long id) throws NoSuchEntityException;

}
