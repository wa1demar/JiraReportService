package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.*;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintDeveloperMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintMapper;
import com.swansoftwaresolutions.jirareport.core.service.SprintService;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;
import com.swansoftwaresolutions.jirareport.domain.entity.builder.SprintBuilder;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintDeveloperRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public SprintDto add(NewSprintDto sprintDto) throws NoSuchEntityException {
        Report report = reportRepository.findById(sprintDto.getReportId());
        if (sprintDto.getName() == null || sprintDto.getName().equals("")) {
            sprintDto.setName("Report " + report.getLastSprintIndex());
            report.setLastSprintIndex(report.getLastSprintIndex()+1);
            reportRepository.update(report);
        }
        return sprintMapper.toDto(sprintRepository.add(sprintMapper.fromDto(sprintDto)));
    }

    @Override
    public SprintDto update(SprintDto sprintDto) {
        return sprintMapper.toDto(sprintRepository.update(sprintMapper.fromDto(sprintDto)));
    }

    @Override
    public SprintDtos findByReportId(long reportId) throws NoSuchEntityException {
        Report report = reportRepository.findById(reportId);
        List<Sprint> sprints = new ArrayList<>(report.getSprints());
        if (report.getTypeId() == 1) {
            List<Sprint> spr = new ArrayList<>();
            for (Sprint s : sprints) {
                if (s.getJiraSprint() != null) {
                    spr.add(new SprintBuilder()
                            .id(s.getId())
                            .name(s.getJiraSprint().getName())
                            .startDate(s.getJiraSprint().getStartDate())
                            .endDate(s.getJiraSprint().getEndDate())
                            .state(s.getJiraSprint().getState())
                            .type(s.getType())
                            .showUAT(s.isShowUAT())
                            .notCountTarget(s.isNotCountTarget())
                            .jiraSprint(s.getJiraSprint())
                            .showOutOfRange(s.isShowOutOfRange())
                            .build());
                }
            }

            return sprintMapper.toDto(spr);
        } else {
            return sprintMapper.toDto(sprints);
        }

    }

    @Override
    public SprintDto delete(long sprintId, long reportId) throws NoSuchEntityException {
        Report report = reportRepository.findById(reportId);

        Sprint sprint = null;
        if (report.getTypeId() != 2) {
            developerRepository.deleteBySprintId(sprintId);
            sprint = sprintRepository.findById(sprintId);
        } else {
            sprint = sprintRepository.delete(sprintId);
        }
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
                developer.setJiraUser(jiraUserRepository.findByLogin(dto.getDeveloperName())); // TODO: push from loop
                developer.setSprint(newSprint);
                SprintDeveloper newDeveloper = developerRepository.add(developer); //TODO ?
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
    public FullSprintDto update(FullSprintDto sprintDto) throws NoSuchEntityException {
        Sprint sprint = sprintMapper.fromDto(sprintDto);

        List<SprintDeveloper> developers = new ArrayList<>();
        for (SprintDeveloperDto dto : sprintDto.getSprintTeams()) {
            SprintDeveloper developer = developerMapper.fromDto(dto);
            try {
                developer.setJiraUser(jiraUserRepository.findByLogin(dto.getDeveloperLogin())); //TODO push from loop
                developer.setSprint(sprint);
                developers.add(developer);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }

        }

        // TODO to many queries
        Sprint oldSprint = sprintRepository.findById(sprintDto.getId());

        sprint.setDevelopers(developers);

        sprint.setReport(reportRepository.findById(sprintDto.getReportId()));
        sprint.setJiraSprint(oldSprint.getJiraSprint());

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

    @Override
    public List<FullSprintDto> findByReport(Report report) throws NoSuchEntityException {
        List<FullSprintDto> fullSprintDto = new ArrayList<>();
        List<Sprint> sprints = new ArrayList<>(report.getSprints());

        List<Long> ids = sprints.stream().map(Sprint::getId).collect(Collectors.toList());
        List<SprintDeveloper> allDevelopers = developerRepository.findByIds(ids);
        Map<Long, List<SprintDeveloper>> devMap = createMap(allDevelopers);


        for (Sprint sprint : sprints) {
            List<SprintDeveloperDto> developers = new ArrayList<>();

            List<SprintDeveloper> devs = devMap.get(sprint.getId());
            if (devs == null) {
                devs = new ArrayList<>();
            }
            for (SprintDeveloper developer : devs) {
                SprintDeveloperDto developerDto = developerMapper.toDto(developer);
                developerDto.setDeveloperName(developer.getJiraUser().getFullName() != null ? developer.getJiraUser().getFullName() : "NoName");
                developerDto.setDeveloperLogin(developer.getJiraUser().getLogin() != null ? developer.getJiraUser().getLogin() : "NoName");
                developers.add(developerDto);
            }


            FullSprintDto fullSpr = new FullSprintDto();
            fullSpr.setId(sprint.getId());
            fullSpr.setType(sprint.getType());
            fullSpr.setReportId(sprint.getReport().getId());
            fullSpr.setNotCountTarget(sprint.isNotCountTarget());
            fullSpr.setShowUat(sprint.isShowUAT());
            fullSpr.setDevelopers(developers);
            fullSpr.setShowOutOfRange(sprint.isShowOutOfRange());
            fullSpr.setIssues(sprint.getIssues());
            fullSpr.setDescription(sprint.getDescription());

            if (sprint.getJiraSprint() != null) {
                fullSpr.setCompleteDate(sprint.getJiraSprint().getCompleteDate());
                fullSpr.setJiraSprintId(sprint.getJiraSprint().getId());
                fullSpr.setStartDate(sprint.getJiraSprint().getStartDate());
                fullSpr.setEndDate(sprint.getJiraSprint().getEndDate());
                fullSpr.setName(sprint.getJiraSprint().getName());
                fullSpr.setState(sprint.getJiraSprint().getState());
            } else {
                fullSpr.setStartDate(sprint.getStartDate());
                fullSpr.setEndDate(sprint.getEndDate());
                fullSpr.setCompleteDate(sprint.getEndDate());
                fullSpr.setName(sprint.getName());
                fullSpr.setState(sprint.getState());

            }

            fullSprintDto.add(fullSpr);
        }

        return fullSprintDto;
    }

    private Map<Long, List<SprintDeveloper>> createMap(List<SprintDeveloper> allDevelopers) {
        Map<Long, List<SprintDeveloper>> result = new HashMap<>();

        for (SprintDeveloper d : allDevelopers) {
            if (result.get(d.getSprint().getId()) == null) {
                List<SprintDeveloper> devs = new ArrayList<>();
                devs.add(d);

                result.put(d.getSprint().getId(), devs);
            } else {
                List<SprintDeveloper> devs = result.get(d.getSprint().getId());
                devs.add(d);
                result.put(d.getSprint().getId(), devs);
            }
        }

        return result;
    }
}
