package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.*;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintDeveloperMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintMapper;
import com.swansoftwaresolutions.jirareport.core.service.SprintDeveloperService;
import com.swansoftwaresolutions.jirareport.core.service.SprintService;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintDeveloperRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class SprintServiceImpl implements SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private JiraUserRepository jiraUserRepository;

    @Autowired
    private SprintDeveloperRepository developerRepository;

    @Autowired
    private SprintMapper sprintMapper;

    @Autowired
    private SprintDeveloperMapper developerMapper;

    @Override
    public SprintDto add(NewSprintDto sprintDto) {
        return sprintMapper.toDto(sprintRepository.add(sprintMapper.fromDto(sprintDto)));
    }

    @Override
    public SprintDto update(SprintDto sprintDto) {
        return sprintMapper.toDto(sprintRepository.update(sprintMapper.fromDto(sprintDto)));
    }

    @Override
    public SprintDtos findByReportId(long reportId) {
        List<Sprint> sprints = sprintRepository.findByReportId(reportId);

        return sprintMapper.toDto(sprints);
    }

    @Override
    public SprintDto delete(long sprintId) throws NoSuchEntityException {
        Sprint sprint = sprintRepository.delete(sprintId);
        return sprintMapper.toDto(sprint);
    }

    @Override
    public SprintDto findById(long sprintId) throws NoSuchEntityException {
        Sprint sprint = sprintRepository.findById(sprintId);
        return sprintMapper.toDto(sprint);
    }

    @Override
    public FullSprintDto add(FullSprintDto sprintDto) {
        Sprint sprint = sprintMapper.fromDto(sprintDto);
        Sprint newSprint = sprintRepository.add(sprint);

        Set<SprintDeveloper> developers = new HashSet<>();
        for (SprintDeveloperDto dto : sprintDto.getDevelopers()) {
            SprintDeveloper developer = developerMapper.fromDto(dto);
            try {
                developer.setJiraUser(jiraUserRepository.findByLogin(dto.getDeveloperName()));
                developer.setSprint(newSprint);
                SprintDeveloper newDeveloper = developerRepository.add(developer);
                developers.add(newDeveloper);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
        }

        return new FullSprintDtoBuilder()
                .id(newSprint.getId())
                .name(newSprint.getName())
                .showUat(newSprint.isShowUAT())
                .notCountTarget(newSprint.isNotCountTarget())
                .state(newSprint.getState())
                .type(newSprint.getType())
                .endDate(newSprint.getEndDate())
                .startDate(newSprint.getStartDate())
                .reportId(newSprint.getReport() != null ? newSprint.getReport().getId() : null)
                .developers(developerMapper.toDtos(developers))
                .build();
    }


}
