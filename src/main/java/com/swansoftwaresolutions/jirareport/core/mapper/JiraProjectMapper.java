package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraProjectDto;

import java.util.List;

/**
 * @author Vitaliy Hollovko
 */
public interface JiraProjectMapper {

    JiraProjectDto toDto(JiraProject jiraProjectEntity);
    List<JiraProjectDto> toDtos(List<JiraProject> jiraProjectEntities);
    JiraProject fromDto(JiraProjectDto jiraProjectDto);
    List<JiraProject> fromDtos(JiraProjectDto jiraProjectDto);

    List<JiraProject> fromDtos(List<ImportedProjectDto> importedProjectsDtos);
}
