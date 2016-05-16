package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDtos;

/**
 * @author Vladimir Martynyuk
 */
public interface ProjectService {
    ProjectDto add(ProjectDto projectDto);

    ProjectDtos findAll();

    void delete(Long id);
}
