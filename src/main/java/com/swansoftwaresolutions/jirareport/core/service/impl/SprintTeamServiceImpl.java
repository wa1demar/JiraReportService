package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.SprintTeamsDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintTeamMapper;
import com.swansoftwaresolutions.jirareport.core.service.SprintTeamService;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class SprintTeamServiceImpl implements SprintTeamService {

    @Autowired
    SprintTeamRepository teamRepository;

    @Autowired
    SprintTeamMapper teamMapper;

    @Override
    public SprintTeamsDto getAll() {
        return teamMapper.toDto(teamRepository.findAll());
    }
}
