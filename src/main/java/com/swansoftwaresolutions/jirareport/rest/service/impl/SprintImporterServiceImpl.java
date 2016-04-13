package com.swansoftwaresolutions.jirareport.rest.service.impl;

import com.swansoftwaresolutions.jirareport.core.mapper.JiraSprintMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraBoardRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraSprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ReportRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.rest.service.SprintImporterService;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.ImportedJiraSprintDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ImportedSprintsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class SprintImporterServiceImpl implements SprintImporterService {

    @Autowired
    @Qualifier("restClient")
    RestClient restClient;

    @Autowired
    JiraBoardRepository jiraBoardRepository;

    @Autowired
    JiraSprintRepository jiraSprintRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    SprintRepository sprintRepository;

    @Autowired
    JiraSprintMapper jiraSprintMapper;

    @Override
    public void loadSprintsFromJiraByBoard() {

        List<JiraBoard> boards = jiraBoardRepository.findAll();

        for (JiraBoard board : boards) {
            ImportedSprintsDto importedSprintsDto = restClient.loadAllSprintsByBoard(board);
            List<ImportedJiraSprintDto> sprints = Arrays.asList(importedSprintsDto.getValues());

            jiraSprintRepository.add(jiraSprintMapper.fromShedullerDtos(sprints), board.getId());
        }


    }

    @Override
    public void addNewSprintsToExitingProjects() {
        List<Report> existedReports = reportRepository.findAllAutomatic();

        if (existedReports.size() > 0) {

            for (Report report : existedReports) {
                List<JiraSprint> sprints = jiraSprintRepository.findByBoardId(report.getBoardId()); // TODO: move from loop

                sprintRepository.addOrUpdate(sprints, report);

            }
        }


    }
}
