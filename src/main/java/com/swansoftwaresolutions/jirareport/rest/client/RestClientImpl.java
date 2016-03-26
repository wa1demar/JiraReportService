package com.swansoftwaresolutions.jirareport.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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

    @Value("${jira.url}")
    private String jiraUrl;

    @Value("${jira.groups}")
    private String jiraGroups;

    @Value("${jira.users}")
    private String jiraUsers;

    @Override
    public JiraGroupsDto loadAllGroups() {

        return restTemplate.exchange(jiraUrl + jiraGroups, HttpMethod.GET, request, JiraGroupsDto.class).getBody();
    }

    @Override
    public ImportedJiraUsersDto loadAllUsersByGroupName(String name) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("group_name", name);
        return restTemplate.exchange(jiraUrl + jiraUsers, HttpMethod.GET, request, ImportedJiraUsersDto.class, params).getBody();
    }


    @Override
    public void loadData() {
        // TODO: will remove
    }
}
