package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.*;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintDeveloperMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintMapper;
import com.swansoftwaresolutions.jirareport.core.service.SprintService;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintDeveloperRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private ReportRepository reportRepository;

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

        List<SprintDeveloper> developers = new ArrayList<>();
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

    @Override
    public FullSprintDto update(FullSprintDto sprintDto) {
        Sprint sprint = sprintMapper.fromDto(sprintDto);

        List<SprintDeveloper> developers = new ArrayList<>();
        for (SprintDeveloperDto dto : sprintDto.getDevelopers()) {
            SprintDeveloper developer = developerMapper.fromDto(dto);
            try {
                developer.setJiraUser(jiraUserRepository.findByLogin(dto.getDeveloperName()));
                developer.setSprint(sprint);
                developers.add(developer);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }

        }

        sprint.setDevelopers(developers);

        sprint.setReport(reportRepository.findById(sprintDto.getReportId()));

        Sprint updatedSprint = sprintRepository.update(sprint);

        List<Long> ids = updatedSprint.getDevelopers().stream().map(sd -> sd.getId()).collect(Collectors.toList());

        developerRepository.delete(ids, updatedSprint.getId());


        return new FullSprintDtoBuilder()
                .id(updatedSprint.getId())
                .name(updatedSprint.getName())
                .showUat(updatedSprint.isShowUAT())
                .notCountTarget(updatedSprint.isNotCountTarget())
                .state(updatedSprint.getState())
                .type(updatedSprint.getType())
                .endDate(updatedSprint.getEndDate())
                .startDate(updatedSprint.getStartDate())
                .reportId(updatedSprint.getReport() != null ? updatedSprint.getReport().getId() : null)
                .developers(developerMapper.toDtos(updatedSprint.getDevelopers()))
                .build();
    }


}
