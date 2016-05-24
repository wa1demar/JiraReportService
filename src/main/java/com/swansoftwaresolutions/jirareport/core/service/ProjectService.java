package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.projects.*;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

/**
 * @author Vladimir Martynyuk
 */
public interface ProjectService {
    ProjectDto add(ProjectDto projectDto);

    ProjectDtos findAll();

    void delete(Long id);

    FullProjectDtos findAllFull(ProjectFilterData filterData);

    ProjectDto update(ProjectDto projectDto);

    FullProjectDto deleteMember(String login, Long id) throws NoSuchEntityException;
}
