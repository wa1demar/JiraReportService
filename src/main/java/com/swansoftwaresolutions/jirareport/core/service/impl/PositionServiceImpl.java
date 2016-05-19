package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.position.PositionDto;
import com.swansoftwaresolutions.jirareport.core.dto.position.PositionDtos;
import com.swansoftwaresolutions.jirareport.core.mapper.PositionMapper;
import com.swansoftwaresolutions.jirareport.core.service.PositionService;
import com.swansoftwaresolutions.jirareport.domain.entity.Position;
import com.swansoftwaresolutions.jirareport.domain.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    PositionMapper positionMapper;

    @Override
    public PositionDtos findAll() {
        List<Position> positions = positionRepository.findAll();

        PositionDtos positionDtos = new PositionDtos();
        positionDtos.setItems(positionMapper.fromPositionsToPositionsDto(positions));
        return positionDtos;
    }

    @Override
    public PositionDto add(PositionDto positionDto) {
        Position position = positionRepository.add(positionMapper.fromPositionDtoToPosition(positionDto));

        return positionMapper.fromPositionToPositionDto(position);
    }

    @Override
    public PositionDto delete(Long id) {
        Position position = positionRepository.delete(id);
        return positionMapper.fromPositionToPositionDto(position);
    }

    @Override
    public PositionDto update(PositionDto positionDto) {
        Position position = positionRepository.update(positionMapper.fromPositionDtoToPosition(positionDto));
        return positionMapper.fromPositionToPositionDto(position);
    }


}
