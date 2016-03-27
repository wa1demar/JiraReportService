package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ProjectDto;

import java.util.List;

/**
 * @author Vitaliy Hollovko
 */
public interface ProjectMapper {

    ProjectDto toDto(JiraProject jiraProjectEntity);
    List<ProjectDto> toDtos(List<JiraProject> jiraProjectEntities);
    JiraProject fromDto(ProjectDto projectDto);
    List<JiraProject> fromDtos(ProjectDto projectDto);

    List<JiraProject> fromDtos(List<ImportedProjectDto> importedProjectsDtos);
}
