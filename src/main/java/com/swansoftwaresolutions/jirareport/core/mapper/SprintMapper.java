package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.ImportedSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintsDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface SprintMapper {
    List<Sprint> fromDtos(List<com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto> sprintDtos);

    List<Sprint> fromShedullerDtos(List<ImportedSprintDto> sprintDtos);

    SprintsDto toDto(List<Sprint> sprints);

    SprintDto toDto(Sprint sprint);

    Sprint fromDto(SprintDto sprint);

    Sprint fromDto(ImportedSprintDto sprint);

    List<ImportedSprintDto> toDtos(List<Sprint> all);
}
