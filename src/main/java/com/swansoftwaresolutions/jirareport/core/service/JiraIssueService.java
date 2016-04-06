package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraIssueDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraIssueService {

    JiraIssueDto save(JiraIssueDto jiraBoard);

    JiraIssueDto update(JiraIssueDto jiraBoard) throws NoSuchEntityException;

    List<JiraIssueDto> findAll() throws NoSuchEntityException;

    List<JiraIssueDto> findBySprintId(long spprintId) throws NoSuchEntityException;

    void delete(JiraIssueDto jiraIssueDto) throws NoSuchEntityException;

    List<JiraIssueDto>  findBySprintIds(List<Long> ids);
}
