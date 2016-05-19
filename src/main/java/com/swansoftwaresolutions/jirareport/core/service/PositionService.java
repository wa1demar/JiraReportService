package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.position.PositionDto;
import com.swansoftwaresolutions.jirareport.core.dto.position.PositionDtos;

/**
 * @author Vladimir Martynyuk
 */
public interface PositionService {
    PositionDtos findAll();

    PositionDto add(PositionDto positionDto);

    PositionDto delete(Long id);

    PositionDto update(PositionDto positionDto);
}
