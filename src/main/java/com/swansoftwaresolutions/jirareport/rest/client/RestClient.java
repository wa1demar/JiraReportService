package com.swansoftwaresolutions.jirareport.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;

/**
 * Created by viholovko on 02.03.16.
 */
public interface RestClient {
    void loadData(); //TODO: change

    JiraGroupsDto loadAllGroups();
}
