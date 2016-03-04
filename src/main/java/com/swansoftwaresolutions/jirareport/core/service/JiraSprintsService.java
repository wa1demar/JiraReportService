package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.entity.Sprint;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraSprintsService {

    Sprint save(Sprint sprint);

    List<Sprint> findAll();

    void delete(Sprint sprint) throws NoSuchEntityException;

    void delete(Long sprintId) throws NoSuchEntityException;
}
