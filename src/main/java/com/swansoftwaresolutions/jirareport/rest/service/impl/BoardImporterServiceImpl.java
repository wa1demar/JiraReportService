package com.swansoftwaresolutions.jirareport.rest.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraSprintMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.ProjectMapper;
import com.swansoftwaresolutions.jirareport.core.service.JiraBoardService;
import com.swansoftwaresolutions.jirareport.core.service.JiraSprintsService;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.repository.ProjectRepository;
import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.rest.service.BoardImporterService;
import com.swansoftwaresolutions.jirareport.rest.service.ProjectImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class BoardImporterServiceImpl implements BoardImporterService {

    @Autowired
    @Qualifier("restClient")
    RestClient restClient;

    @Autowired
    JiraBoardService jiraBoardService;


    @Autowired
    ProjectService projectService;

    @Autowired
    JiraSprintsService sprintsService;

    @Autowired
    JiraSprintMapper jiraSprintMapper;


    @Override
    public void importBoardsFromJira() {

    }
}
