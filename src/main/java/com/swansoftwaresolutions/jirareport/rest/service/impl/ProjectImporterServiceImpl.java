package com.swansoftwaresolutions.jirareport.rest.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraProjectMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraProjectRepository;
import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
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
public class ProjectImporterServiceImpl implements ProjectImporterService {

    @Autowired
    @Qualifier("restClient")
    RestClient restClient;

    @Autowired
    JiraProjectMapper jiraJiraProjectMapper;

    @Autowired
    JiraProjectRepository jiraProjectRepository;

    @Override
    public void importProjectsFromJira() {
        List<ImportedProjectDto> importedProjectsDtos = Arrays.asList(restClient.loadAllProjects());
        List<JiraProject> projects = jiraJiraProjectMapper.fromDtos(importedProjectsDtos);
        jiraProjectRepository.save(projects);
    }
}
