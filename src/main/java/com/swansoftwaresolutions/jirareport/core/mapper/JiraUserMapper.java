package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraUserDto;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface JiraUserMapper {
    JiraUserDto toDto(JiraUser jiraUserEntity);
    List<JiraUserDto> toDtos(List<JiraUser> jiraUserEntities);
    JiraUser fromDto(JiraUserDto jiraUserDto);
    List<JiraUser> fromDtos(JiraUserDto jiraUserDto);
}
