package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.sprint_issue.IssuesByDayDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_issue.SprintIssueListDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface SprintIssueService {

    List<SprintIssueDto> findAll() throws NoSuchEntityException;

    SprintIssueListDto findBySprintIdAndAsignee(Long sprintId, String assignee);

    SprintIssueListDto findBySprintId(Long sprintId);

    SprintIssueDto add(SprintIssueDto sprintIssueDto);

    SprintIssueDto update(SprintIssueDto sprintIssueDto);

    void delete(Long issueId) throws NoSuchEntityException;

    List<IssuesByDayDto> getIssuesByDay(Long sprintId, String assignee);
}
