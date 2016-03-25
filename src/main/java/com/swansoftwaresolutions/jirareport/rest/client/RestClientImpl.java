package com.swansoftwaresolutions.jirareport.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    @Override
    public JiraGroupsDto loadAllGroups() {

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(jiraUrl + jiraGroups, HttpMethod.GET, request, JiraGroupsDto.class).getBody();
    }


    @Override
    public void loadData() {
        // TODO: will remove
    }
}
