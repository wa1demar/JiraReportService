package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.NewSprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDevelopersDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDevelopersDtoBuilder;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintDeveloperMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.mappings.SprintDeveloperDtoMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Vladimir Martynyuk
 */

@Component
public class SprintDeveloperMapperImpl implements SprintDeveloperMapper {

    ModelMapper modelMapper;

    @Autowired
    public SprintDeveloperMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.addMappings(new SprintDeveloperDtoMapper());
    }

    @Override
    public SprintDevelopersDto toDto(List<SprintDeveloper> developers) {
        Type targetistType = new TypeToken<List<SprintDeveloperDto>>(){}.getType();
        List<SprintDeveloperDto> developersDto = modelMapper.map(developers, targetistType);
        return new SprintDevelopersDtoBuilder().developers(developersDto).build();
    }

    @Override
    public SprintDeveloper fromDto(NewSprintDeveloperDto dto) {
        return modelMapper.map(dto, SprintDeveloper.class);
    }

    @Override
    public SprintDeveloperDto toDto(SprintDeveloper newDeveloper) {
        return modelMapper.map(newDeveloper, SprintDeveloperDto.class);
    }

    @Override
    public List<SprintDeveloper> fromDtos(List<SprintDeveloperDto> developers) {
        Type targetistType = new TypeToken<List<SprintDeveloperDto>>(){}.getType();
        return modelMapper.map(developers, targetistType);
    }

    @Override
    public List<SprintDeveloperDto> toDtos(List<SprintDeveloper> developers) {
        Type targetistType = new TypeToken<List<SprintDeveloperDto>>(){}.getType();
        return modelMapper.map(developers, targetistType);
    }

    @Override
    public SprintDeveloper fromDto(SprintDeveloperDto dto) {
        return modelMapper.map(dto, SprintDeveloper.class);
    }
}
