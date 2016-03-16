package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.*;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
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
public class SprintMapperImpl implements SprintMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Sprint fromDto(NewSprintDto sprintDto) {
        return modelMapper.map(sprintDto, Sprint.class);
    }

    @Override
    public SprintDto toDto(Sprint sprint) {
        return modelMapper.map(sprint, SprintDto.class);
    }

    @Override
    public Sprint fromDto(SprintDto sprintDto) {
        return modelMapper.map(sprintDto, Sprint.class);
    }

    @Override
    public SprintDtos toDto(List<Sprint> sprints) {

        Type targetistType = new TypeToken<List<SprintDto>>(){}.getType();
        List<SprintDto> sprintDtos = modelMapper.map(sprints, targetistType);

        return new SprintDtosBuilder().sprints(sprintDtos).build();
    }

    @Override
    public Sprint fromDto(FullSprintDto sprintDto) {
        return modelMapper.map(sprintDto, Sprint.class);
    }

}
