package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.NewSprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDevelopersDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintDeveloperMapper;
import com.swansoftwaresolutions.jirareport.core.service.SprintDeveloperService;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintDeveloperRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class SprintIssueServiceImpl implements SprintDeveloperService {

    @Autowired
    SprintDeveloperRepository developerRepository;

    @Autowired
    JiraUserRepository userRepository;

    @Autowired
    SprintRepository sprintRepository;

    @Autowired
    SprintDeveloperMapper developerMapper;

    @Override
    public SprintDevelopersDto findBySprint(long sprintId) {
        List<SprintDeveloper> developers = developerRepository.findBySprintId(sprintId);
        return developerMapper.toDto(developers);
    }

    @Override
    public SprintDeveloperDto add(NewSprintDeveloperDto dto, long sprintId) throws NoSuchEntityException {
        SprintDeveloper developer = developerMapper.fromDto(dto);
        developer.setJiraUser(userRepository.findByLogin(dto.getDeveloperName()));
        developer.setSprint(sprintRepository.findById(sprintId));

        SprintDeveloper newDeveloper = developerRepository.add(developer);
        return developerMapper.toDto(newDeveloper);
    }


}
