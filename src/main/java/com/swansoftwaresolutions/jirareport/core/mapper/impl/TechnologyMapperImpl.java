package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.technologies.FullTechnologyDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyDto;
import com.swansoftwaresolutions.jirareport.core.mapper.TechnologyMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Technology;
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
public class TechnologyMapperImpl implements TechnologyMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<TechnologyDto> fromTechnologiesToTechnologiesDto(List<Technology> technologies) {
        Type targetistType = new TypeToken<List<TechnologyDto>>() {}.getType();
        return modelMapper.map(technologies, targetistType);
    }

    @Override
    public Technology fromTechnologyDtoToTechnology(TechnologyDto technologyDto) {
        return modelMapper.map(technologyDto, Technology.class);
    }

    @Override
    public TechnologyDto fromTechnologyToTechnologyDto(Technology technology) {
        return modelMapper.map(technology, TechnologyDto.class);
    }

    @Override
    public Technology fromTechnologiesDtoToTechnologies(List<TechnologyDto> technologiesDto) {
        Type targetistType = new TypeToken<List<Technology>>() {}.getType();
        return modelMapper.map(technologiesDto, targetistType);
    }

    @Override
    public List<FullTechnologyDto> fromTechnologiesToFullTechnologiesDto(List<Technology> technologies) {

        Type targetistType = new TypeToken<List<FullTechnologyDto>>() {}.getType();
        return modelMapper.map(technologies, targetistType);
    }

    @Override
    public FullTechnologyDto fromTechnologyToFullTechnologyDto(Technology technology) {
        return modelMapper.map(technology, FullTechnologyDto.class);
    }
}
