package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.ImportedJiraSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.JiraSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.JiraSprintsDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraSprintsService {

    JiraSprintDto add(JiraSprintDto sprint);

    void add(ImportedJiraSprintDto sprint);

    void addOrUpdate(ImportedJiraSprintDto sprint);

    List<ImportedJiraSprintDto> findAll() throws NoSuchEntityException;

    void delete(JiraSprint jiraSprint) throws NoSuchEntityException;

    void delete(Long sprintId) throws NoSuchEntityException;

    JiraSprintsDto retrieveByBoardId(long board_id);

    JiraSprintDto findById(long id);

}
