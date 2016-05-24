package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.projects.*;
import com.swansoftwaresolutions.jirareport.core.mapper.ProjectMapper;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUsersReferences;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUsersReferencesRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ProjectRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ResourceBordRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    JiraUsersReferencesRepository jiraUsersReferencesRepository;

    @Autowired
    JiraUserRepository jiraUserRepository;

    @Autowired
    ResourceBordRepository resourceBordRepository;

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

        List<JiraUser> jiraUsers = jiraUsersReferencesRepository.findUsersByProjectId(id);
        ResourceColumn resourceColumn = resourceBordRepository.findDefaultColumn();

        for (JiraUser user : jiraUsers) {
            if (user.getUserReferences().size() > 1) {
                jiraUsersReferencesRepository.deleteByProject(user.getLogin(), id);
            } else {
                jiraUsersReferencesRepository.deleteByProject(user.getLogin(), id);

                JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
                jiraUsersReferences.setColumn(resourceColumn);
                jiraUsersReferences.setUser(user);
                jiraUsersReferencesRepository.add(jiraUsersReferences);
            }
        }

        projectRepository.delete(id);
    }

    @Override
    public FullProjectDtos findAllFull(ProjectFilterData filterData) {
        List<Project> projects = projectRepository.findAll();
        List<FullProjectDto> projectDtoList = projectMapper.fromProjectsToFullProjectDtos(projects, filterData);

        FullProjectDtos projectDtos = new FullProjectDtos();
        projectDtos.setProjects(projectDtoList);

        return projectDtos;
    }

    @Override
    public ProjectDto update(ProjectDto projectDto) {
        Project project = projectRepository.update( projectMapper.fromProjectDtoToProject(projectDto));
        return projectMapper.fromProjectToProjectDto(project);
    }

    @Override
    public FullProjectDto deleteMember(String login, Long id) throws NoSuchEntityException {
        Project project = projectRepository.findById(id);
        JiraUser jiraUser = jiraUserRepository.findByLogin(login);

        jiraUsersReferencesRepository.deleteByProject(jiraUser.getLogin(), project.getId());

        jiraUser = jiraUserRepository.findByLogin(login);
        if (jiraUser.getUserReferences().size() == 0) {
            ResourceColumn bench = resourceBordRepository.findDefaultColumn();
            JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
            jiraUsersReferences.setColumn(bench);
            jiraUsersReferences.setUser(jiraUser);
            jiraUsersReferencesRepository.add(jiraUsersReferences);

            jiraUser.setUserReferences(new ArrayList<JiraUsersReferences>(){{
                add(jiraUsersReferences); }});

        }

        return projectMapper.fromProjectToFullProjectDto(project);
    }
}
