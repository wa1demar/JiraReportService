package com.swansoftwaresolutions.jirareport.core.repository;


import com.swansoftwaresolutions.jirareport.core.entity.SprintIssue;

import java.util.List;

public interface SprintIssueRepository {

    List<SprintIssue> getAllSprintIssues();

    List<SprintIssue> getSprintIssuesByIdSprint(final Long sprintId);

    List<SprintIssue> getSprintIssuesByIdSprintAndAssignee(final Long sprintId, final String assignee);

    List<SprintIssue> getSprintIssuesByIdSprintAndAssigneeAndIssueDate(final Long sprintId, final String assignee, final String issueDate);

    SprintIssue getSprintIssueById(final Long id);

    void createSprintIssue(final SprintIssue sprintIssue);

    void updateSprintIssue(final SprintIssue sprintIssue);

    void deleteAllSprintIssue();

    void deleteSprintIssue(final SprintIssue sprint);

    void deleteSprintIssue(final Long id);

    void deleteSprintIssuesByIdSprint(final Long sprintId);

    void deleteSprintIssuesByIdSprintAndAssignee(final Long sprintId, final String assignee);
}
