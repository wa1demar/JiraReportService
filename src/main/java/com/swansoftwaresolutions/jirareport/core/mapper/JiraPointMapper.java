package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.JiraPointDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraPoint;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraPointMapper {

    JiraPointDto toDto(JiraPoint jiraPoint);
    List<JiraPointDto> toDtos(List<JiraPoint> jiraPointList);
    JiraPoint fromDto(JiraPointDto jiraPointDto);
    List<JiraPoint> fromDtos(List<JiraPointDto> jiraPointDto);

}
