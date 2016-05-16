package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDtos;
import com.swansoftwaresolutions.jirareport.core.mapper.ProjectMapper;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.domain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public ProjectDto add(ProjectDto projectDto) {
        Project project = projectMapper.fromProjectDtoToProject(projectDto);
        Project newProject = projectRepository.save(project);
        return projectMapper.fromProjectToProjectDto(newProject);
    }

    @Override
    public ProjectDtos findAll() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectDto> projectDtoList = projectMapper.fromProjectsToProjectDtos(projects);

        ProjectDtos projectDtos = new ProjectDtos();
        projectDtos.setProjects(projectDtoList);

        return projectDtos;
    }

    @Override
    public void delete(Long id) {
        projectRepository.delete(id);
    }
}
