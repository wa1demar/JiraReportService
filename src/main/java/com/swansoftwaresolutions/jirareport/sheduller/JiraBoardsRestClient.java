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

    @Autowired
    JiraBoardService jiraBoardService;

    @Autowired
    ProjectService projectService;

    public void loadData() {
        log.info("+++++++++++++++++++++++++++++++++++");
        log.info("-----------------------------------");
        log.info("-------Jira Board Scheduler--------");

        final String uri = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board.json";

        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        JiraBoardObjectDto jiraBoardDtos = restTemplate.exchange(uri, HttpMethod.GET, request, JiraBoardObjectDto.class).getBody();

        log.info("   Boards on Cloud : " + jiraBoardDtos);
        insertDataToDataBase(jiraBoardDtos.values);

        log.info("--- Jira Board Scheduler Completed ---");
        log.info("--------------------------------------");
        log.info("");
    }

    private void insertDataToDataBase(JiraBoardDto[] jiraBoardDtos) {
        List<JiraBoard> jiraBoards = fromDtos(jiraBoardDtos);
        List<JiraBoard> jiraBoardsDB = jiraBoardService.findAll();
        removeDublicateAndSave(jiraBoards, jiraBoardsDB);
    }

    private void deleteOldProjects(List<Project> projects, List<Project> projectsDB) {
        projectsDB.removeAll(new HashSet(projects));
        for (Project project : projectsDB) {
//            try {
////                jiraBoardService.delete(jiraBoard);
//            } catch (NoSuchEntityException e) {
//                e.printStackTrace();
//            }
        }
    }

    private void removeDublicateAndSave(List<JiraBoard> jiraBoards, List<JiraBoard> projectsDB) {
        List<JiraBoard> jiraBoardList = jiraBoards;
        jiraBoardList.removeAll(new HashSet(projectsDB));

        log.info("   Removed " + (jiraBoards.size() - jiraBoardList.size()) + " dublicates");
        for (JiraBoard jiraBoard : jiraBoardList) {
            if (jiraBoard.getProjectKey()!= null) {
                jiraBoardService.save(jiraBoard);
            }
        }
    }

    private List<JiraBoard> fromDtos(JiraBoardDto[] jiraBoardDtos) {
       List<JiraBoard> jiraBoards = new ArrayList<>();
        for (JiraBoardDto jiraBoardDto : jiraBoardDtos){
            jiraBoards.add(fromDto(getProjectInformationForBoard(jiraBoardDto)));
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

    private JiraBoardDto getProjectInformationForBoard(JiraBoardDto jiraBoardDto) {
        final String uri = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board/"+jiraBoardDto.id+"/issue.json";
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        IssuesForJiraBoard jiraBoardDtos = restTemplate.exchange(uri, HttpMethod.GET, request, IssuesForJiraBoard.class).getBody();

        if (jiraBoardDtos.issues.length>0){
            jiraBoardDto.projectKey = jiraBoardDtos.issues[0].fields.project.key;
            try {
                jiraBoardDto.projectId = projectService.findByKey(jiraBoardDto.projectKey).getJiraId();
            } catch (NoSuchEntityException e){

            }
        } else {
            System.out.println("Empty Project Key for " + jiraBoardDto.name);
        }

        return jiraBoardDto;
    }


}
