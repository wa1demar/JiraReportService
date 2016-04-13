package com.swansoftwaresolutions.jirareport.rest.service;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;

/**
 * @author Vladimir Martynyuk
 */
public interface SprintImporterService {
    void loadSprintsFromJiraByBoard();
    void addNewSprintsToExitingProjects();
}
