package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ProjectDto;

import java.util.List;

/**
 * @author Vitaliy Hollovko
 */
public interface ProjectMapper {

    ProjectDto toDto(Project projectEntity);
    List<ProjectDto> toDtos(List<Project> projectEntities);
    Project fromDto(ProjectDto projectDto);
    List<Project> fromDtos(ProjectDto projectDto);
}
