package com.swansoftwaresolutions.jirareport.sheduller.rest.client;

import com.swansoftwaresolutions.jirareport.core.service.JiraBoardService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.sheduller.dto.*;
import com.swansoftwaresolutions.jirareport.sheduller.job.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */

@Component("issueRestClient")
public class IssueRestClient extends RestClientBase implements RestClient {

    static Logger log = Logger.getLogger(IssueRestClient.class.getName());

    final String URL_SPRINT = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/board/{boardId}/sprint";
    final String URL_ISSUES_BY_SPRINT = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/sprint/{sprintId}/issue?maxResults=500";


    @Autowired
    JiraBoardService jiraBoardService;

    @Override
    public void loadData() {
        List<JiraBoard> jiraBoardList = jiraBoardService.findAll();

        for (JiraBoard jiraBoard: jiraBoardList) {
            HttpEntity<String> request = new HttpEntity<>(getHeaders());
            RestTemplate restTemplate = new RestTemplate();
            AgileSprintsDto agileSprints = restTemplate.exchange(URL_SPRINT.replace("{boardId}", String.valueOf(jiraBoardList.get(1).getBoardId())), HttpMethod.GET, request, AgileSprintsDto.class).getBody();

            for (AgileSprintDto agileSprintDto : agileSprints.values) {
                IssuesDto issues = restTemplate.exchange(URL_ISSUES_BY_SPRINT.replace("{sprintId}", String.valueOf(agileSprintDto.id)), HttpMethod.GET, request, IssuesDto.class).getBody();
                for (IssueDto issueDto : issues.issues) {
                    convertIssueDtoToJiraIssueDto(issueDto);
                }

            }
        }

    }

    private JiraIssueDto convertIssueDtoToJiraIssueDto(IssueDto issueDto) {
        JiraIssueDto jiraIssueDto = new JiraIssueDto();
        jiraIssueDto.setKey(issueDto.key);
        jiraIssueDto.setProjectId(issueDto.fields.project.getId());
        jiraIssueDto.setProjectKey(issueDto.fields.project.getKey());
        jiraIssueDto.setIssueTypeId(issueDto.fields.issuetype.id);
        jiraIssueDto.setIssueTypeName(issueDto.fields.issuetype.name);
        jiraIssueDto.setIssueTypeSubTask(issueDto.fields.issuetype.subtask);
        jiraIssueDto.setTimeSpent(issueDto.fields.timespent);
        jiraIssueDto.setResolutionId(issueDto.fields.resolution.id);
        jiraIssueDto.setResolutionName(issueDto.fields.resolution.name);
        jiraIssueDto.setCreated(issueDto.fields.created);
        jiraIssueDto.setUpdated(issueDto.fields.updated);
        jiraIssueDto.setAssignetKey(issueDto.fields.assignee.key);
        jiraIssueDto.setAssignetName(issueDto.fields.assignee.name);
        jiraIssueDto.setAssignetFullName(issueDto.fields.assignee.displayName);
        jiraIssueDto.setCreatorKey(issueDto.fields.creator.key);
        jiraIssueDto.setCreatorName(issueDto.fields.creator.name);
        jiraIssueDto.setCreatorFullName(issueDto.fields.creator.displayName);
        jiraIssueDto.setStatusId(issueDto.fields.status.id);
        jiraIssueDto.setStatusName(issueDto.fields.status.name);
        jiraIssueDto.setDueDate(issueDto.fields.duedate);
        jiraIssueDto.setPoints(issueDto.fields.customfield_10102);
        return jiraIssueDto;
    }

}
