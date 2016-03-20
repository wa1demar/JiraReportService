package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.FullSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.NewSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDtos;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

/**
 * @author Vladimir Martynyuk
 */
public interface SprintService {

    SprintDto add(NewSprintDto sprintDto);

    SprintDto update(SprintDto sprintDto);

    SprintDtos findByReportId(long reportId) throws NoSuchEntityException;

    SprintDto delete(long sprintId) throws NoSuchEntityException;

    SprintDto findById(long sprintId) throws NoSuchEntityException;

    FullSprintDto add(FullSprintDto sprintDto);

    FullSprintDto update(FullSprintDto sprintDto) throws NoSuchEntityException;
}
