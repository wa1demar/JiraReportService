package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MoveMemberToProject;
import com.swansoftwaresolutions.jirareport.core.dto.projects.*;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.FullTechnologyDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.ProjectMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.ResourceBordMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.TechnologyMapper;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.core.service.ResourceBordService;
import com.swansoftwaresolutions.jirareport.domain.entity.*;
import com.swansoftwaresolutions.jirareport.domain.repository.*;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ResourceBordService resourceBordService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TechnologyRepository technologyRepository;

    @Autowired
    JiraUsersReferencesRepository jiraUsersReferencesRepository;

    @Autowired
    JiraUserRepository jiraUserRepository;

    @Autowired
    ResourceBordRepository resourceBordRepository;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    TechnologyMapper technologyMapper;

    @Autowired
    ResourceBordMapper resourceBordMapper;

    @Autowired
    JiraUserMapper jiraUserMapper;

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
    public ExportProjectsDtos findAllForExport(ProjectFilterData filterData) {
        List<Project> projects = projectRepository.findAll();
        List<FullProjectDto> projectDtoList = projectMapper.fromProjectsToFullProjectDtos(projects, filterData);
        ResourceColumnDto resourceColumnDto = resourceBordMapper.fromResourceColumnToResourceColumnDto(resourceBordRepository.findDefaultColumn());

        List<JiraUser> users = jiraUserRepository.findFromBench();

        Map<String, Set<JiraUserDto>> listMap = new HashMap<>();
        for (JiraUser user : users) {
            JiraUserDto userDto = jiraUserMapper.fromJiraUserToJiraUserDto(user);
            List<Technology> technologies = user.getTechnologies();
            for (Technology technology : technologies) {
                FullTechnologyDto technologyDto = technologyMapper.fromTechnologyToFullTechnologyDto(technology);
                Set<JiraUserDto> jiraUsers = listMap.get(technologyDto.getName());
                if (jiraUsers == null) {
                    jiraUsers = new HashSet<>();
                }

                userDto.setAssignmentType(resourceColumnDto);
                jiraUsers.add(userDto);
                listMap.put(technologyDto.getName(), jiraUsers);
            }
        }

        ExportProjectsDtos exportProjectsDtos = new ExportProjectsDtos();
        exportProjectsDtos.setProjects(projectDtoList);
        exportProjectsDtos.setTechnologies(listMap);
        return exportProjectsDtos;
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

    @Override
    public FullProjectDtos moveMember(String login, MoveMemberToProject moveMemberToProject) throws NoSuchEntityException {
        JiraUser jiraUser = jiraUserRepository.findByLogin(login);
        ProjectMoveIds projectMoveIds = moveMemberToProject.getProjects();

        if (projectMoveIds != null) {
            jiraUsersReferencesRepository.deleteByProject(jiraUser.getLogin(), projectMoveIds.getFromProjectId());
            ResourceColumn resourceColumn = resourceBordRepository.findById(moveMemberToProject.getAssignmentTypeId());
            Project project = projectRepository.findById(projectMoveIds.getToProjectId());

            if (project != null) {
                    JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
                    jiraUsersReferences.setUser(jiraUser);
                    jiraUsersReferences.setColumn(resourceColumn);
                    jiraUsersReferences.setProject(project);

                    jiraUsersReferencesRepository.add(jiraUsersReferences);

            }
        }

        projectRepository.sortMembers(moveMemberToProject.getUsers(), moveMemberToProject.getProjects().getToProjectId());

        return findAllFull(moveMemberToProject.getFilters());
    }

    @Override
    public FullProjectDtos copyMember(String login, MoveMemberToProject moveMemberToProject) throws NoSuchEntityException {
        JiraUser jiraUser = jiraUserRepository.findByLogin(login);
        ProjectMoveIds projectMoveIds = moveMemberToProject.getProjects();

        if (projectMoveIds != null) {

            ResourceColumn resourceColumn = resourceBordRepository.findById(moveMemberToProject.getAssignmentTypeId());
            Project project = projectRepository.findById(projectMoveIds.getToProjectId());

            if (project != null) {
                JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
                jiraUsersReferences.setUser(jiraUser);
                jiraUsersReferences.setColumn(resourceColumn);
                jiraUsersReferences.setProject(project);

                jiraUsersReferencesRepository.add(jiraUsersReferences);

            }
        }

        projectRepository.sortMembers(moveMemberToProject.getUsers(), moveMemberToProject.getProjects().getToProjectId());

        return findAllFull(moveMemberToProject.getFilters());
    }

    @Override
    public FullProjectDtos sortProjects(ProjectPositionDto projectPositionDto) {
        projectRepository.sort(projectPositionDto.getItems());

        return findAllFull(projectPositionDto.getFilters());
    }
}
