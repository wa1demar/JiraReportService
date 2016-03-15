package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.ImportedSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintsDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraSprintsService {

    SprintDto add(SprintDto sprint);

    void add(ImportedSprintDto sprint);

    List<ImportedSprintDto> findAll();

    void delete(Sprint sprint) throws NoSuchEntityException;

    void delete(Long sprintId) throws NoSuchEntityException;

    SprintsDto retrieveByReportId(long reportId);

}
