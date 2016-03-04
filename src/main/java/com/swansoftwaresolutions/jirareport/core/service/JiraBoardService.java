package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraBoardService {

    JiraBoard save(JiraBoard jiraBoard);

    List<JiraBoard> findAll();

    void delete(JiraBoard jiraBoard) throws NoSuchEntityException;
}
