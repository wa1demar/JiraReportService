package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface JiraUserMapper {
    JiraUserDto toDto(JiraUser jiraUserEntity);
    List<JiraUserDto> toDtos(List<JiraUser> jiraUserEntities);
    JiraUser fromDto(JiraUserDto jiraUserDto);
    List<JiraUser> fromDtos(JiraUserDto jiraUserDto);

    List<JiraUser> fromDtos(List<ImportedJiraUserDto> usersList);

    ResourceUserDto fromJiraUserToResourceUserDto(JiraUser jiraUser);

    com.swansoftwaresolutions.jirareport.core.dto.jira_users.JiraUserDto fromJiraUserToJiraUserDto(JiraUser user);
}
