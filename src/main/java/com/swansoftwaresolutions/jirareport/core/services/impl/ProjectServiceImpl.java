package com.swansoftwaresolutions.jirareport.core.services.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Project;
import com.swansoftwaresolutions.jirareport.core.repository.ProjectRepository;
import com.swansoftwaresolutions.jirareport.core.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by viholovko on 02.03.16.
 */

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Project save(Project project) {
        return projectRepository.add(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    @Override
    public void delete(Project project) {
        projectRepository.deleteProject(project);
    }
}
