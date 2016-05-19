package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.position.PositionDto;
import com.swansoftwaresolutions.jirareport.core.mapper.PositionMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Position;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class PositionMapperImpl implements PositionMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<PositionDto> fromPositionsToPositionsDto(List<Position> positions) {
        Type targetistType = new TypeToken<List<PositionDto>>() {}.getType();
        return modelMapper.map(positions, targetistType);
    }

    @Override
    public Position fromPositionDtoToPosition(PositionDto positionDto) {
        return modelMapper.map(positionDto, Position.class);
    }

    @Override
    public PositionDto fromPositionToPositionDto(Position position) {
        return modelMapper.map(position, PositionDto.class);
    }
}
