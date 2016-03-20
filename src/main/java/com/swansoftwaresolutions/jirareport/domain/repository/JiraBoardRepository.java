package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraBoardRepository {

    JiraBoard add(JiraBoard jiraBoard);

    List<JiraBoard> findAll() throws NoSuchEntityException;

    JiraBoard findById(Long jiraBoardId);

    void delete(final JiraBoard jiraBoard) throws NoSuchEntityException;

    void delete(final Long jiraBoard) throws NoSuchEntityException;

    JiraBoard update(JiraBoard jiraBoard) throws NoSuchEntityException;
}
