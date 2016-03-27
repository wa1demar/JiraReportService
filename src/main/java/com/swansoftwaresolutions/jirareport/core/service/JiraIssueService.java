package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardInfoDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardsDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
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

    void delete(JiraIssueDto jiraIssueDto) throws NoSuchEntityException;


}
