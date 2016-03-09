package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.sheduller.dto.SprintDto;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface SprintMapper {
    List<Sprint> fromDtos(List<SprintDto> sprintDtos);
}
