package com.swansoftwaresolutions.jirareport.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.ImportedBardsDto;
import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;
import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.sheduller.dto.IssuesDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ImportedSprintsDto;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Martynyuk
 */
@Component("restClient")
@PropertySource("classpath:jira.properties")
public class RestClientImpl extends AbstractRestClient implements RestClient {

    Logger log = LogManager.getLogger(RestClientImpl.class);

    @Value("${jira.url}")
    private String jiraUrl;

    @Value("${jira.groups}")
    private String jiraGroups;

    @Value("${jira.users}")
    private String jiraUsers;

    @Value("${jira.projects}")
    private String jiraProjects;

    @Value("${jira.issues}")
    private String jiraIssue;

    @Value("${jira.boards}")
    private String jiraBoards;

    @Value("${jira.sprints}")
    private String jiraSprints;

    @Autowired
    ConfigService configService;

    @Override
    public JiraGroupsDto loadAllGroups() {
        ConfigDto configDto = configService.retrieveConfig();
        HttpEntity<String> request = new HttpEntity<>(getHeaders(configDto.getJiraUser(), configDto.getJiraPass()));
        return restTemplate.exchange(jiraUrl + jiraGroups, HttpMethod.GET, request, JiraGroupsDto.class).getBody();
    }

    @Override
    public ImportedJiraUsersDto loadAllUsersByGroupName(String name) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("group_name", name);
        ConfigDto configDto = configService.retrieveConfig();
        HttpEntity<String> request = new HttpEntity<>(getHeaders(configDto.getJiraUser(), configDto.getJiraPass()));
        return restTemplate.exchange(jiraUrl + jiraUsers, HttpMethod.GET, request, ImportedJiraUsersDto.class, params).getBody();
    }

    @Override
    public ImportedProjectDto[] loadAllProjects() {
        log.info("Import projects");
        ConfigDto configDto = configService.retrieveConfig();
        HttpEntity<String> request = new HttpEntity<>(getHeaders(configDto.getJiraUser(), configDto.getJiraPass()));
        return restTemplate.exchange(jiraUrl + jiraProjects, HttpMethod.GET, request, ImportedProjectDto[].class).getBody();
    }


    @Override
    public IssuesDto loadAllIssues(String sprintId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("sprint_id", sprintId);
        ConfigDto configDto = configService.retrieveConfig();
        HttpEntity<String> request = new HttpEntity<>(getHeaders(configDto.getJiraUser(), configDto.getJiraPass()));
        return restTemplate.exchange(jiraUrl + jiraIssue, HttpMethod.GET, request, IssuesDto.class, params).getBody();
    }
    @Override
    public ImportedBardsDto loadAllBoardsByProjectKey(String key) {
        log.info("Import boards for Project " + key);
        Map<String, String> params = new HashMap<String, String>();
        params.put("project_key", key);

        ConfigDto configDto = configService.retrieveConfig();
        HttpEntity<String> request = new HttpEntity<>(getHeaders(configDto.getJiraUser(), configDto.getJiraPass()));
        return restTemplate.exchange(jiraUrl + jiraBoards, HttpMethod.GET, request, ImportedBardsDto.class, params).getBody();

    }

    @Override
    public ImportedSprintsDto loadAllSprintsByBoard(JiraBoard board) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("board_id", String.valueOf(board.getBoardId()));

        ConfigDto configDto = configService.retrieveConfig();
        HttpEntity<String> request = new HttpEntity<>(getHeaders(configDto.getJiraUser(), configDto.getJiraPass()));

        return restTemplate.exchange(jiraUrl + jiraSprints, HttpMethod.GET, request, ImportedSprintsDto.class, params).getBody();
    }


    @Override
    public void loadData() {
        // TODO: going to remove
    }
}
