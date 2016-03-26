package com.swansoftwaresolutions.jirareport.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;

import java.util.List;

/**
 * Created by viholovko on 02.03.16.
 */
public interface RestClient {
    void loadData(); //TODO: change

    JiraGroupsDto loadAllGroups();

    ImportedJiraUsersDto loadAllUsersByGroupName(String name);
}
