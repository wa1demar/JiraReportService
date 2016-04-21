package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

public interface JiraSprintRepository {

    List<JiraSprint> findAll() throws NoSuchEntityException;

    List<JiraSprint> findByBoardId(final Long boardId);

    JiraSprint findById(final Long id);

    JiraSprint add(final JiraSprint jiraSprint);

    JiraSprint addOrUpdate(JiraSprint jiraSprint);

    JiraSprint update(final JiraSprint jiraSprint) throws NoSuchEntityException;

    void deleteAll();

    void delete(Long id) throws NoSuchEntityException;

    JiraSprint findByNameAndBoardId(String name, Long boardId) throws NoSuchEntityException;

    void add(List<JiraSprint> sprints, JiraBoard board);

    void setDeleted(Long sprintId);
}
