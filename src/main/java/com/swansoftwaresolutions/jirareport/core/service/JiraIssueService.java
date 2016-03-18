package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardInfoDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardsDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraIssueService {

    JiraBoard save(JiraBoard jiraBoard);

    List<JiraBoard> findAll();

    void delete(JiraBoard jiraBoard) throws NoSuchEntityException;

    List<JiraBoardInfoDto> findAllBoardForInfo();

    JiraBoardsDto retrieveAllBoards();

}
