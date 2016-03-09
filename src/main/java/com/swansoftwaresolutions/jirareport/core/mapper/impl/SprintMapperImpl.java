package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.mapper.SprintMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.sheduller.dto.SprintDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class SprintMapperImpl implements SprintMapper {
    @Override
    public List<Sprint> fromDtos(List<SprintDto> sprintDtos) {
        return null;
    }
}
