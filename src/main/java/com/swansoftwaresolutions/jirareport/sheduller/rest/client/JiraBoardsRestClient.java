package com.swansoftwaresolutions.jirareport.sheduller.rest.client;

import com.swansoftwaresolutions.jirareport.core.mapper.SprintMapper;
import com.swansoftwaresolutions.jirareport.core.service.JiraBoardService;
import com.swansoftwaresolutions.jirareport.core.service.JiraSprintsService;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.sheduller.dto.*;
import com.swansoftwaresolutions.jirareport.sheduller.job.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Vitaliy Holovko
 */

@Component("jiraBoardsRestClient")
public class JiraBoardsRestClient extends RestClientBase implements RestClient {

    static Logger log = Logger.getLogger(JiraBoardsRestClient.class.getName());

    final String BOARD_URL = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board.json";
    final String ISSUES_BOARD_URL = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board/BOARDID/issue.json";
    final String SPRINTS_BOARD_URL = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board/BOARDID/sprint.json";

    @Autowired
    JiraBoardService jiraBoardService;


    @Autowired
    ProjectService projectService;

    @Autowired
    JiraSprintsService sprintsService;

    @Autowired
    SprintMapper sprintMapper;


    public void loadData() {
        loadDataForJiraBoards();
        loadDataForJiraSprints();
    }

    private void loadDataForJiraSprints() {
        for (JiraBoard jiraBoard : jiraBoardService.findAll()) {
            log.info("-----------------------------------");
            log.info("-------Jira Board Scheduler--------");

            HttpEntity<String> request = new HttpEntity<>(getHeaders());
            RestTemplate restTemplate = new RestTemplate();
            SprintsDto sprintDtos = restTemplate.exchange(autocompliteIssuesUrl(SPRINTS_BOARD_URL, jiraBoard.getBoardId().intValue()),
                    HttpMethod.GET, request, SprintsDto.class).getBody();

            saveSprintsToDataBase(sprintDtos.getValues());


            log.info("");
        }
    }

    private void saveSprintsToDataBase(List<SprintDto> values) {
        List<Sprint> sprints = sprintMapper.fromShedullerDtos(values);

        //TODO Need to be change
        try {
            sprints.removeAll(sprintsService.findAll());
        } catch (NullPointerException ex) {
            log.warning("NullPointerExeption !!!");
        }

        for (Sprint sprint : sprints) {
            sprintsService.add(sprint);
        }
    }

    private void loadDataForJiraBoards() {
        log.info("-----------------------------------");
        log.info("-------Jira Board Scheduler--------");

        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        JiraBoardObjectDto jiraBoardDtos = restTemplate.exchange(BOARD_URL, HttpMethod.GET, request, JiraBoardObjectDto.class).getBody();

        insertDataToDataBase(jiraBoardDtos.getValues());

        log.info("--- Jira Board Scheduler Completed ---");
        log.info("");
    }

    private void insertDataToDataBase(List<JiraBoardDto> jiraBoardDtos) {
        removeDublicateAndSave(fromDtos(jiraBoardDtos));
    }

    private void removeDublicateAndSave(List<JiraBoard> jiraBoards) {
        jiraBoards.removeAll(jiraBoardService.findAll());

        for (JiraBoard jiraBoard : jiraBoards) {
            if (jiraBoard.getProjectKey() != null) {
                jiraBoardService.save(jiraBoard);
            }
        }
    }

    private JiraBoardDto getProjectInformationForBoard(JiraBoardDto jiraBoardDto) throws NoSuchEntityException {
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        IssuesForJiraBoard jiraBoardDtos = restTemplate.exchange(autocompliteIssuesUrl(ISSUES_BOARD_URL, jiraBoardDto.getId()), HttpMethod.GET, request, IssuesForJiraBoard.class).getBody();

        if (jiraBoardDtos.issues.length > 0) {
            jiraBoardDto.setProjectKey(jiraBoardDtos.issues[0].fields.project.getKey());
            jiraBoardDto.setProjectId(projectService.findByKey(jiraBoardDto.getProjectKey()).getJiraId());
        } else {
            log.warning("Empty Project Key for " + jiraBoardDto.getName());
        }

        return jiraBoardDto;
    }

//    ToDo replace to helper

    private String autocompliteIssuesUrl(String str, int id) {
        return str.replaceAll("BOARDID", String.valueOf(id));
    }

//ToDo replace methods to Service

    private List<JiraBoard> fromDtos(List<JiraBoardDto> jBoardDtos) {
        List<JiraBoard> jiraBoards = new ArrayList<>();
        for (JiraBoardDto jBoardDto : jBoardDtos) {
            try {
                jiraBoards.add(fromDto(getProjectInformationForBoard(jBoardDto)));
            } catch (NoSuchEntityException e) {
                log.warning("Some problems with data from Board " + jBoardDto.getName());
            }
        }
        return jiraBoards;
    }

    private JiraBoard fromDto(JiraBoardDto jBoardDto) {
        JiraBoard jiraBoard = new JiraBoard();
        jiraBoard.setName(jBoardDto.getName());
        jiraBoard.setType(jBoardDto.getType());
        jiraBoard.setBoardId((long) jBoardDto.getId());
        jiraBoard.setProjectKey(jBoardDto.getProjectKey());
        jiraBoard.setProjectJiraId(jBoardDto.getProjectId());

        return jiraBoard;
    }

    private Sprint fromDto(SprintDto sprintDto) {
        Sprint sprint = new Sprint();
        sprint.setName(sprintDto.name);
        sprint.setState(sprintDto.state);
        sprint.setAgileSprintId((long) sprintDto.originBoardId);
        sprint.setStartDate(sprintDto.startDate);
        sprint.setCompleteDate(sprintDto.completeDate);
        sprint.setEndDate(sprintDto.completeDate);

        return sprint;
    }


}
