package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.PointDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Point;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface PointMapper {

    PointDto toDto(Point point);
    List<PointDto> toDtos(List<Point> pointList);
    Point fromDto(PointDto pointDto);
    List<Point> fromDtos(List<PointDto> pointDto);

}
