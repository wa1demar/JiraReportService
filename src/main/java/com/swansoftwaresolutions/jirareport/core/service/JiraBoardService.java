package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardInfoDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardsDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraBoardService {

    JiraBoard save(JiraBoard jiraBoard);

    List<JiraBoard> findAll() throws NoSuchEntityException;

    void delete(JiraBoard jiraBoard) throws NoSuchEntityException;

    List<JiraBoardInfoDto> findAllBoardForInfo() throws NoSuchEntityException;

    JiraBoardsDto retrieveAllBoards()throws NoSuchEntityException;

    JiraBoardDto findById(long id) throws NoSuchEntityException;

}
