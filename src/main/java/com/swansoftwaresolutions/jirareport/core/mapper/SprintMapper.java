package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.FullSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.NewSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDtos;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface SprintMapper {
    Sprint fromDto(NewSprintDto sprintDto);

    SprintDto toDto(Sprint sprint);

    Sprint fromDto(SprintDto sprintDto);

    SprintDtos toDto(List<Sprint> sprints);

    Sprint fromDto(FullSprintDto sprintDto);

}
