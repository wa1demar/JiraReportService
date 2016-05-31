package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.technologies.FullTechnologyDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Technology;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface TechnologyMapper {
    List<TechnologyDto> fromTechnologiesToTechnologiesDto(List<Technology> technologies);

    Technology fromTechnologyDtoToTechnology(TechnologyDto technologyDto);

    TechnologyDto fromTechnologyToTechnologyDto(Technology technology);

    Technology fromTechnologiesDtoToTechnologies(List<TechnologyDto> technologiesDto);

    List<FullTechnologyDto> fromTechnologiesToFullTechnologiesDto(List<Technology> technologies);

    FullTechnologyDto fromTechnologyToFullTechnologyDto(Technology technology);
}
