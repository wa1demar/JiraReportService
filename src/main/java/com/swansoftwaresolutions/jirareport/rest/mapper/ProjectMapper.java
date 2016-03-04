package com.swansoftwaresolutions.jirareport.rest.mapper;

import com.swansoftwaresolutions.jirareport.core.entity.Project;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ProjectDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */
public interface ProjectMapper {

    ProjectDto toDto(Project projectEntity);
    List<ProjectDto> toDtos(List<Project> projectEntities);
    Project fromDto(ProjectDto projectDto);
    List<Project> fromDtos(ProjectDto projectDto);
}
