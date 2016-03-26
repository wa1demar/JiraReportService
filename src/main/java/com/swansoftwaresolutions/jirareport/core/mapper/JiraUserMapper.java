package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUserDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserAutoDto;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface JiraUserMapper {
    JiraUserDto toDto(JiraUser jiraUserEntity);
    List<JiraUserDto> toDtos(List<JiraUser> jiraUserEntities);
    JiraUser fromDto(JiraUserDto jiraUserDto);
    List<JiraUser> fromDtos(JiraUserDto jiraUserDto);


    JiraUserAutoDto toAutoDto(JiraUser jiraUser);
    List<JiraUserAutoDto> toAutoDtos(List<JiraUser> jiraUser);
    JiraUser fromAutoDto(JiraUserAutoDto jiraUserAutoDto);
    List<JiraUser> fromAutoDtos(List<JiraUserAutoDto> jiraUserAutoDtoList);

    List<JiraUser> fromDtos(List<ImportedJiraUserDto> usersList);
}
