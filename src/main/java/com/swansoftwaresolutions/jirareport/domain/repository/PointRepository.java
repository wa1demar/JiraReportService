package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraPoint;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface PointRepository {

    List<JiraPoint> findAll();

    JiraPoint findById(Long id) throws NoSuchEntityException;

    JiraPoint add(JiraPoint jiraPoint);

    JiraPoint update(JiraPoint jiraPoint) throws NoSuchEntityException;

    void deleteAll() throws NoSuchEntityException;

    JiraPoint delete(JiraPoint jiraPoint) throws NoSuchEntityException;

    JiraPoint delete(Long pointId) throws NoSuchEntityException;

    JiraPoint findByLoginAndSprintId(String login, Long sprint) throws NoSuchEntityException;

    List<JiraPoint> findByBoardId(Long boardId) throws NoSuchEntityException;
}
