package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.SprintTeamsDto;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintTeam;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface SprintTeamMapper {
    SprintTeamsDto toDto(List<SprintTeam> sprintTeams);
}
