package com.swansoftwaresolutions.jirareport.sheduller;

import com.swansoftwaresolutions.jirareport.core.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.core.entity.Project;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.services.JiraBoardService;
import com.swansoftwaresolutions.jirareport.core.services.ProjectService;
import com.swansoftwaresolutions.jirareport.sheduller.dto.IssuesForJiraBoard;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraBoardDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraBoardObjectDto;
import com.swansoftwaresolutions.jirareport.sheduller.job.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
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

    @Autowired
    JiraBoardService jiraBoardService;

    @Autowired
    ProjectService projectService;

    public void loadData() {
       loadDataForJiraBoards();
    }

    private void loadDataForJiraBoards() {
        log.info("-----------------------------------");
        log.info("-------Jira Board Scheduler--------");

        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        JiraBoardObjectDto jiraBoardDtos = restTemplate.exchange(BOARD_URL, HttpMethod.GET, request, JiraBoardObjectDto.class).getBody();

        log.info("   Boards on Cloud : " + jiraBoardDtos);
        insertDataToDataBase(jiraBoardDtos.values);

        log.info("--- Jira Board Scheduler Completed ---");
        log.info("");
    }

    private void insertDataToDataBase(JiraBoardDto[] jiraBoardDtos) {
        removeDublicateAndSave(fromDtos(jiraBoardDtos));
    }

    private void removeDublicateAndSave(List<JiraBoard> jiraBoards) {
        jiraBoards.removeAll(new HashSet(jiraBoardService.findAll()));

        for (JiraBoard jiraBoard : jiraBoards) {
            if (jiraBoard.getProjectKey()!= null) {
                jiraBoardService.save(jiraBoard);
            }
        }
    }

    private JiraBoardDto getProjectInformationForBoard(JiraBoardDto jiraBoardDto) throws NoSuchEntityException {
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        IssuesForJiraBoard jiraBoardDtos = restTemplate.exchange(autocompliteIssuesUrl(jiraBoardDto.id), HttpMethod.GET, request, IssuesForJiraBoard.class).getBody();

        if (jiraBoardDtos.issues.length>0){
            jiraBoardDto.projectKey = jiraBoardDtos.issues[0].fields.project.key;
            jiraBoardDto.projectId = projectService.findByKey(jiraBoardDto.projectKey).getJiraId();
        } else {
            log.warning("Empty Project Key for " + jiraBoardDto.name);
        }

        return jiraBoardDto;
    }

//    ToDo replace to helper

    private String autocompliteIssuesUrl(int id) {
        return ISSUES_BOARD_URL.replaceAll("BOARDID",String.valueOf(id));
    }

//ToDo replace methods to Service

private List<JiraBoard> fromDtos(JiraBoardDto[] jBoardDtos) {
    List<JiraBoard> jiraBoards = new ArrayList<>();
    for (JiraBoardDto jBoardDto : jBoardDtos){
        try {
            jiraBoards.add(fromDto(getProjectInformationForBoard(jBoardDto)));
        } catch (NoSuchEntityException e){
            log.warning("Some problems with data from Board "+ jBoardDto.name);
        }
    }
    return jiraBoards;
}

    private JiraBoard fromDto(JiraBoardDto jBoardDto) {
        JiraBoard jiraBoard = new JiraBoard();
        jiraBoard.setName(jBoardDto.name);
        jiraBoard.setType(jBoardDto.type);
        jiraBoard.setBoardId((long) jBoardDto.id);
        jiraBoard.setProjectKey(jBoardDto.projectKey);
        jiraBoard.setProjectJiraId(jBoardDto.projectId);

        return jiraBoard;
    }


}
