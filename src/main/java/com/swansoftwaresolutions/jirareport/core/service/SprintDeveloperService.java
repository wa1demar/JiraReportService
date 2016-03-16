package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.NewSprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDevelopersDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

/**
 * @author Vladimir Martynyuk
 */
public interface SprintDeveloperService {

    SprintDevelopersDto findBySprint(long sprintId);

    SprintDeveloperDto add(NewSprintDeveloperDto dto, long sprintId) throws NoSuchEntityException;
}
