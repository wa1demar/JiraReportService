package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.NewSprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDevelopersDto;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;

import java.util.List;
import java.util.Set;

/**
 * @author Vladimir Martynyuk
 */
public interface SprintDeveloperMapper {

    SprintDevelopersDto toDtos(List<SprintDeveloper> developers);

    SprintDeveloper fromDto(NewSprintDeveloperDto dto);

    SprintDeveloperDto toDtos(SprintDeveloper newDeveloper);

    List<SprintDeveloper> fromDtos(List<SprintDeveloperDto> developers);

    List<SprintDeveloperDto> toDtos(Set<SprintDeveloper> developers);

    SprintDeveloper fromDto(SprintDeveloperDto dto);
}
