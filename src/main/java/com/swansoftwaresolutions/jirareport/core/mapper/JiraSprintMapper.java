package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.ImportedJiraSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.JiraSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.JiraSprintsDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface JiraSprintMapper {
//    List<JiraSprint> fromDtos(List<JiraSprintDto> jiraSprintDtos);

    List<JiraSprint> fromShedullerDtos(List<ImportedJiraSprintDto> sprintDtos);

    JiraSprintsDto toDto(List<JiraSprint> jiraSprints);

    JiraSprintDto toDto(JiraSprint jiraSprint);

    JiraSprint fromDto(JiraSprintDto sprint);

    JiraSprint fromDto(ImportedJiraSprintDto sprint);

    List<ImportedJiraSprintDto> toDtos(List<JiraSprint> all);
}
