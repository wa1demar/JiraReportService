package com.swansoftwaresolutions.jirareport.sheduller.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.ImportedJiraSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.ImportedJiraSprintDtos;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraSprintMapper;
import com.swansoftwaresolutions.jirareport.core.service.JiraBoardService;
import com.swansoftwaresolutions.jirareport.core.service.JiraSprintsService;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.sheduller.dto.*;
import com.swansoftwaresolutions.jirareport.sheduller.job.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Vitaliy Holovko
 */

@Component("jiraBoardsRestClient")
public class JiraBoardsRestClient extends RestClientBase implements RestClient {

    static Logger log = Logger.getLogger(JiraBoardsRestClient.class.getName());

    private final String BOARD_URL = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board.json";
    private final String ISSUES_BOARD_URL = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board/{board_id}/fields.json";
    private final String SPRINTS_BOARD_URL = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board/{board_id}/sprint.json";

    @Autowired
    JiraBoardService jiraBoardService;


    @Autowired
    ProjectService projectService;

    @Autowired
    JiraSprintsService sprintsService;

    @Autowired
    JiraSprintMapper jiraSprintMapper;


    @Override
    public void loadData() {
        loadDataForJiraBoards();
        loadDataForJiraSprints();
    }

    private void loadDataForJiraSprints() {
        log.info("get all boards from db");
        List<JiraBoard> boards = jiraBoardService.findAll();
        for (JiraBoard jiraBoard : boards) {
            log.info("-----------------------------------");
            log.info("-------Jira Sprint Scheduler--------");

            HttpEntity<String> request = new HttpEntity<>(getHeaders());
            RestTemplate restTemplate = new RestTemplate();

            Map<String, String> params = new HashMap<String, String>();
            params.put("board_id", String.valueOf(jiraBoard.getBoardId()));

            ResponseEntity<ImportedJiraSprintDtos> responseEntity = restTemplate.exchange(SPRINTS_BOARD_URL, HttpMethod.GET, request, ImportedJiraSprintDtos.class, params);
            log.info("STATUS: " + responseEntity.getStatusCode());
            ImportedJiraSprintDtos sprintDtos = responseEntity.getBody();
            saveSprintsToDataBase(sprintDtos.getValues(), jiraBoard.getId());


            log.info("");
        }
    }

    private void saveSprintsToDataBase(List<ImportedJiraSprintDto> values, long boardId) {
        List<ImportedJiraSprintDto> sprints = sprintsService.findAll();

        //TODO Need to be change
        try {
            values.removeAll(sprints);
        } catch (NullPointerException ex) {
            log.warning("NullPointerExeption !!!");
        }

        for (ImportedJiraSprintDto sprint : values) {
            sprint.setOriginBoardId(boardId);
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

        Map<String, String> params = new HashMap<String, String>();
        params.put("board_id", String.valueOf(jiraBoardDto.getId()));

        IssuesForJiraBoard jiraBoardDtos = restTemplate.exchange(ISSUES_BOARD_URL, HttpMethod.GET, request, IssuesForJiraBoard.class, params).getBody();

        if (jiraBoardDtos.issues.length > 0) {
            jiraBoardDto.setProjectKey(jiraBoardDtos.issues[0].fields.project.getKey());
            jiraBoardDto.setProjectId(projectService.findByKey(jiraBoardDto.getProjectKey()).getJiraId());
        } else {
            log.warning("Empty Project Key for " + jiraBoardDto.getName());
        }

        return jiraBoardDto;
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

}
