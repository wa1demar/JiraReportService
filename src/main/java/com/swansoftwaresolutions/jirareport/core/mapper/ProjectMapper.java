package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.projects.FullProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectFilterData;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ProjectMapper {
    Project fromProjectDtoToProject(ProjectDto projectDto);

    ProjectDto fromProjectToProjectDto(Project project);

    List<ProjectDto> fromProjectsToProjectDtos(List<Project> projects);

    List<FullProjectDto> fromProjectsToFullProjectDtos(List<Project> projects, ProjectFilterData filterData);

    FullProjectDto fromProjectToFullProjectDto(Project project);
}
