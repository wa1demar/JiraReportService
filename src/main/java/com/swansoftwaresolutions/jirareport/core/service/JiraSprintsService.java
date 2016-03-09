package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.SprintsDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraSprintsService {

    Sprint save(Sprint sprint);

    List<Sprint> findAll();

    void delete(Sprint sprint) throws NoSuchEntityException;

    void delete(Long sprintId) throws NoSuchEntityException;

    SprintsDto retrieveByReportId(long reportId, int typeId);
}
