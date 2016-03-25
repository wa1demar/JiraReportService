package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraGroup;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface JiraGroupMapper {
    List<JiraGroup> fromDtos(List<JiraGroupDto> groups);
}
