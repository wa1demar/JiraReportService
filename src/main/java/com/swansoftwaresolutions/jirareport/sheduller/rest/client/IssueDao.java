package com.swansoftwaresolutions.jirareport.sheduller.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.ImportedJiraSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.helper.HelperMethods;
import com.swansoftwaresolutions.jirareport.core.service.*;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.rest.client.AbstractRestClient;
import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.sheduller.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */

@Component("issueClient")
public class IssueDao extends AbstractRestClient implements RestClient {

    static Logger log = Logger.getLogger(IssueDao.class.getName());

    final String URL_ISSUES_BY_SPRINT = "https://swansoftwaresolutions.atlassian.net/rest/agile/1.0/sprint/{sprintId}/issue?maxResults=500";


    @Autowired
    JiraBoardService jiraBoardService;

    @Autowired
    JiraUserService jiraUserService;

    @Autowired
    PointService pointService;

    @Autowired
    ReportService reportService;

    @Autowired
    JiraSprintsService sprintService;

    @Autowired
    JiraIssueService jiraIssueService;

    @Override
    public void loadData() {
        List<ImportedJiraSprintDto> sprints;

        try {
            sprints = sprintService.findAll();
            getAllIsuues(sprints);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JiraGroupsDto loadAllGroups() {
        return null;
    }

    @Override
    public ImportedJiraUsersDto loadAllUsersByGroupName(String name) {
        return null;
    }

    @Override
    public ImportedProjectDto[] loadAllProjects() {
        return new ImportedProjectDto[0];
    }

    private void getAllIsuues(List<ImportedJiraSprintDto> agileSprints) throws NoSuchEntityException {
        for (ImportedJiraSprintDto sprint : agileSprints) {
            if (sprint.getSprintId()!=0) {
                HttpEntity<String> request = new HttpEntity<>(getHeaders());
                RestTemplate restTemplate = new RestTemplate();

                IssuesDto issues = restTemplate.exchange(URL_ISSUES_BY_SPRINT.replace("{sprintId}", String.valueOf(sprint.getSprintId())), HttpMethod.GET, request, IssuesDto.class).getBody();
                for (IssueDto issueDto : issues.issues) {
                    JiraIssueDto jiraIssueDto = convertIssueDtoToJiraIssueDto(issueDto, sprint.getOriginBoardId());
                    jiraIssueDto.setBoardId(sprint.getOriginBoardId());
                    jiraIssueDto.setSprintId(sprint.getId());
                    jiraIssueDto = jiraIssueService.save(jiraIssueDto);
                }
            }
        }

    }

    private JiraIssueDto convertIssueDtoToJiraIssueDto(IssueDto issueDto, long boardId) {
        HelperMethods helperMethods = new HelperMethods();

        JiraIssueDto jiraIssueDto = new JiraIssueDto();

        jiraIssueDto.setKey(issueDto.key);
        jiraIssueDto.setProjectId(issueDto.fields.getProject().getId());
        jiraIssueDto.setProjectKey(issueDto.fields.getProject().getKey());
        jiraIssueDto.setIssueTypeId(issueDto.fields.getIssuetype().id);
        jiraIssueDto.setIssueTypeName(issueDto.fields.getIssuetype().name);
        jiraIssueDto.setIssueTypeSubTask(issueDto.fields.getIssuetype().subtask);
        jiraIssueDto.setTimeSpent(helperMethods.isNull(issueDto.fields.getTimespent()));
        if (issueDto.fields.getResolution() != null) {
            jiraIssueDto.setResolutionId(issueDto.fields.getResolution().id);
            jiraIssueDto.setResolutionName(issueDto.fields.getResolution().name);
        } else {
            jiraIssueDto.setResolutionId(0);
            jiraIssueDto.setResolutionName("");
        }
        jiraIssueDto.setCreated(issueDto.fields.getCreated());
        jiraIssueDto.setUpdated(issueDto.fields.getUpdated());
        jiraIssueDto.setAssignedKey(issueDto.fields.getAssignee().key);
        jiraIssueDto.setAssignedName(issueDto.fields.getAssignee().name);
        jiraIssueDto.setAssignedFullName(issueDto.fields.getAssignee().displayName);
        jiraIssueDto.setCreatorKey(issueDto.fields.getCreator().key);
        jiraIssueDto.setCreatorName(issueDto.fields.getCreator().name);
        jiraIssueDto.setCreatorFullName(issueDto.fields.getCreator().displayName);
        jiraIssueDto.setStatusId(issueDto.fields.getStatus().id);
        jiraIssueDto.setStatusName(issueDto.fields.getStatus().name);
        jiraIssueDto.setDueDate(issueDto.fields.getDuedate());
        jiraIssueDto.setPoints(issueDto.fields.getCustomfield_10102());
        jiraIssueDto.setBoardId(boardId);
        return jiraIssueDto;
    }

}
