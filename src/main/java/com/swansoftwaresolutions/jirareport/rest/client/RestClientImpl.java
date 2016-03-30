package com.swansoftwaresolutions.jirareport.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;
import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vladimir Martynyuk
 */
@Component("restClient")
@PropertySource("classpath:jira.properties")
public class RestClientImpl extends AbstractRestClient implements RestClient {

    @Value("${jira.url}")
    private String jiraUrl;

    @Value("${jira.groups}")
    private String jiraGroups;

    @Value("${jira.users}")
    private String jiraUsers;

    @Value("${jira.projects}")
    private String jiraProjects;

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
        ConfigDto configDto = configService.retrieveConfig();
        HttpEntity<String> request = new HttpEntity<>(getHeaders(configDto.getJiraUser(), configDto.getJiraPass()));
        return restTemplate.exchange(jiraUrl + jiraProjects, HttpMethod.GET, request, ImportedProjectDto[].class).getBody();
    }


    @Override
    public void loadData() {
        // TODO: will remove
    }
}
