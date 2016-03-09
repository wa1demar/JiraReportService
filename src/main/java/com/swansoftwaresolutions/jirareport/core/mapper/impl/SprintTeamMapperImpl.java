package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.SprintTeamDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintTeamsDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintTeamMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintTeam;
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
public class SprintTeamMapperImpl implements SprintTeamMapper {

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public SprintTeamsDto toDto(List<SprintTeam> sprintTeams) {
        Type targetistType = new TypeToken<List<SprintTeamDto>>(){}.getType();
        List<SprintTeamDto> teamDtos = modelMapper.map(sprintTeams, targetistType);

        return new SprintTeamsDto(teamDtos);
    }
}
