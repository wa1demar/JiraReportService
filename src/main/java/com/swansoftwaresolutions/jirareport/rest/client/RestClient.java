package com.swansoftwaresolutions.jirareport.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.dto.ImportedBardsDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ImportedSprintsDto;

/**
 * Created by viholovko on 02.03.16.
 */
public interface RestClient {
    void loadData(); //TODO: change

    JiraGroupsDto loadAllGroups();

    ImportedJiraUsersDto loadAllUsersByGroupName(String name);

    ImportedProjectDto[] loadAllProjects();

    ImportedBardsDto loadAllBoardsByProjectKey(String key);

    ImportedSprintsDto loadAllSprintsByBoard(JiraBoard board);
}
