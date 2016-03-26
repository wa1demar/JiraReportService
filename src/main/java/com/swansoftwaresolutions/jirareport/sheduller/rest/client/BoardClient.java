package com.swansoftwaresolutions.jirareport.sheduller.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.ImportedJiraSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraSprintMapper;
import com.swansoftwaresolutions.jirareport.core.service.JiraBoardService;
import com.swansoftwaresolutions.jirareport.core.service.JiraSprintsService;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.rest.client.AbstractRestClient;
import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.sheduller.dto.*;
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

@Component("boardClient")
public class BoardClient extends AbstractRestClient implements RestClient {

    static Logger log = Logger.getLogger(RestClient.class.getName());

    final String URL_SPRINT = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board/{boardId}/sprint";

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

    @Override
    public JiraGroupsDto loadAllGroups() {
        return null;
    }

    @Override
    public ImportedJiraUsersDto loadAllUsersByGroupName(String name) {
        return null;
    }

    private void loadDataForJiraSprints() {
        List<JiraBoard> boards = new ArrayList<>();
        try {
            boards = jiraBoardService.findAll();
        } catch (NoSuchEntityException e) {
            log.warning("List<JiraBoard> NoSuchEntityException");
        }
        for (JiraBoard jiraBoard : boards) {
            HttpEntity<String> request = new HttpEntity<>(getHeaders());
            RestTemplate restTemplate = new RestTemplate();

            AgileSprintsDto agileSprints = restTemplate.exchange(URL_SPRINT.replace("{boardId}", String.valueOf(jiraBoard.getBoardId())), HttpMethod.GET, request, AgileSprintsDto.class).getBody();
            saveSprintsToDataBase(agileSprints.values, jiraBoard.getId());
        }
    }

    private void saveSprintsToDataBase(AgileSprintDto[] values, long boardId) {
        List<ImportedJiraSprintDto> list = new ArrayList<>();
        for (AgileSprintDto agileSprintDto: values){
            ImportedJiraSprintDto importedJiraSprintDto = new ImportedJiraSprintDto();
            importedJiraSprintDto.setName(agileSprintDto.name);
            importedJiraSprintDto.setState(agileSprintDto.state);
            importedJiraSprintDto.setCompleteDate(agileSprintDto.completedDate!=null ? agileSprintDto.completedDate : null);
            importedJiraSprintDto.setEndDate(agileSprintDto.endDate);
            importedJiraSprintDto.setStartDate(agileSprintDto.startDate);
            importedJiraSprintDto.setOriginBoardId(boardId);
            importedJiraSprintDto.setSprintId(agileSprintDto.id);

            list.add(importedJiraSprintDto);
        }

        List<ImportedJiraSprintDto> sprints = new ArrayList<>();

        try {
            sprints = sprintsService.findAll();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        if (sprints!=null) list.removeAll(sprints);

        list.forEach(sprintsService::addOrUpdate);
    }

    private void loadDataForJiraBoards() {
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        String BOARD_URL = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board";
        JiraBoardObjectDto jiraBoardDtos = restTemplate.exchange(BOARD_URL, HttpMethod.GET, request, JiraBoardObjectDto.class).getBody();

        insertDataToDataBase(jiraBoardDtos.getValues());
    }

    private void insertDataToDataBase(List<JiraBoardDto> jiraBoardDtos) {
        removeDublicateAndSave(fromDtos(jiraBoardDtos));
    }

    private void removeDublicateAndSave(List<JiraBoard> jiraBoards) {
        try {
            jiraBoards.removeAll(jiraBoardService.findAll());
        } catch (NoSuchEntityException e) {
            log.warning("List<JiraBoard> NoSuchEntityException");
        }

        jiraBoards.stream().filter(jiraBoard -> jiraBoard.getProjectKey() != null).forEach(jiraBoardService::save);
    }

    private JiraBoardDto getProjectInformationForBoard(JiraBoardDto jiraBoardDto) throws NoSuchEntityException {
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();

        String ISSUES_BOARD_URL = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board/{boardId}/issue";
        IssuesForJiraBoard jiraBoardDtos = restTemplate.exchange(ISSUES_BOARD_URL.replace("{boardId}", String.valueOf(jiraBoardDto.getId())), HttpMethod.GET, request, IssuesForJiraBoard.class).getBody();

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
