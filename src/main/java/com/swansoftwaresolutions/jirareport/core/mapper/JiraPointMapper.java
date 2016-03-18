package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.PointDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraPoint;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraPointMapper {

    PointDto toDto(JiraPoint jiraPoint);
    List<PointDto> toDtos(List<JiraPoint> jiraPointList);
    JiraPoint fromDto(PointDto pointDto);
    List<JiraPoint> fromDtos(List<PointDto> pointDto);

}
