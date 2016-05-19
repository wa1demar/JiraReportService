package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.position.PositionDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Position;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface PositionMapper {
    List<PositionDto> fromPositionsToPositionsDto(List<Position> positions);

    Position fromPositionDtoToPosition(PositionDto positionDto);

    PositionDto fromPositionToPositionDto(Position position);
}
