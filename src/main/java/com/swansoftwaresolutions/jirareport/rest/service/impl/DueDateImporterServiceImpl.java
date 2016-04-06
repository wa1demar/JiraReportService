package com.swansoftwaresolutions.jirareport.rest.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ProjectMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.repository.ProjectRepository;
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
public class DueDateImporterServiceImpl implements ProjectImporterService {

    @Autowired
    @Qualifier("restClient")
    RestClient restClient;

    @Autowired
    ProjectMapper jiraProjectMapper;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public void importProjectsFromJira() {
        List<ImportedProjectDto> importedProjectsDtos = Arrays.asList(restClient.loadAllProjects());
        List<JiraProject> projects = jiraProjectMapper.fromDtos(importedProjectsDtos);
        projectRepository.save(projects);
    }
}
