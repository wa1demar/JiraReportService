package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
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
}
