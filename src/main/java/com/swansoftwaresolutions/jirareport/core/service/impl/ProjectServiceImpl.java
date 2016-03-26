package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.repository.ProjectRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public JiraProject save(JiraProject jiraProject) {
        return projectRepository.add(jiraProject);
    }

    @Override
    public JiraProject findByKey(String key) throws NoSuchEntityException {
        return projectRepository.findByKey(key);
    }

    @Override
    public List<JiraProject> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public void delete(JiraProject jiraProject) throws NoSuchEntityException {
        projectRepository.delete(jiraProject);
    }
}
