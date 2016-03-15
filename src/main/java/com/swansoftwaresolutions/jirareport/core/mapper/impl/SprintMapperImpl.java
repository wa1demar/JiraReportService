package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.ImportedSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintsDto;
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
    public List<Sprint> fromDtos(List<SprintDto> sprintDtos) {
        return null;
    }

    @Override
    public List<Sprint> fromShedullerDtos(List<ImportedSprintDto> sprintDtos) {
        Type targetistType = new TypeToken<List<Sprint>>(){}.getType();
        return modelMapper.map(sprintDtos, targetistType);
    }

    @Override
    public SprintsDto toDto(List<Sprint> sprints) {
        SprintsDto sprintsDto = new SprintsDto();
        Type targetistType = new TypeToken<List<SprintDto>>(){}.getType();
        sprintsDto.setSprints(modelMapper.map(sprints, targetistType));

        return sprintsDto;
    }

    @Override
    public SprintDto toDto(Sprint sprint) {
        return modelMapper.map(sprint, SprintDto.class);
    }

    @Override
    public Sprint fromDto(SprintDto sprint) {
        return modelMapper.map(sprint, Sprint.class);
    }

    @Override
    public Sprint fromDto(ImportedSprintDto sprint) {
        return modelMapper.map(sprint, Sprint.class);
    }

    @Override
    public List<ImportedSprintDto> toDtos(List<Sprint> all) {
        Type targetistType = new TypeToken<List<ImportedSprintDto>>(){}.getType();
        return modelMapper.map(all, targetistType);
    }
}
