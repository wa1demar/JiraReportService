package com.swansoftwaresolutions.jirareport.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.IssueDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.IssuesDto;
import com.swansoftwaresolutions.jirareport.core.dto.ImportedBardsDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ImportedSprintsDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface RestClient {

    JiraGroupsDto loadAllGroups();

    ImportedJiraUsersDto loadAllUsersByGroupName(String name);

    ImportedProjectDto[] loadAllProjects();

    IssuesDto loadAllIssues(String sprintId);

    ImportedBardsDto loadAllBoardsByProjectKey(String key);

    ImportedSprintsDto loadAllSprintsByBoard(JiraBoard board);
}
