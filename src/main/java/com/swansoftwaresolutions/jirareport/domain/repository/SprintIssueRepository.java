package com.swansoftwaresolutions.jirareport.domain.repository;


import com.swansoftwaresolutions.jirareport.domain.entity.SprintIssue;

import java.util.List;

public interface SprintIssueRepository {

    List<SprintIssue> getAllSprintIssues();

    List<SprintIssue> getSprintIssuesBySprintId(final Long sprintId);

    List<SprintIssue> getSprintIssuesBySprintIdAndAssignee(final Long sprintId, final String assignee);

    List<SprintIssue> getSprintIssuesBySprintIdAndAssigneeAndIssueDate(final Long sprintId, final String assignee, final String issueDate);

    SprintIssue getSprintIssueById(final Long id);

    void createSprintIssue(final SprintIssue sprintIssue);

    void updateSprintIssue(final SprintIssue sprintIssue);

    void deleteAllSprintIssue();

    void deleteSprintIssue(final SprintIssue sprint);

    void deleteSprintIssue(final Long id);

    void deleteSprintIssuesByIdSprint(final Long sprintId);

    void deleteSprintIssuesBySprintIdAndAssignee(final Long sprintId, final String assignee);
}
