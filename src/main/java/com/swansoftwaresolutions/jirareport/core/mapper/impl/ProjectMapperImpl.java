package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullProjectUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.FullProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ProjectMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUsersReferences;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Project fromProjectDtoToProject(ProjectDto projectDto) {
        return modelMapper.map(projectDto, Project.class);
    }

    @Override
    public ProjectDto fromProjectToProjectDto(Project project) {
        return modelMapper.map(project, ProjectDto.class);
    }

    @Override
    public List<ProjectDto> fromProjectsToProjectDtos(List<Project> projects) {
        Type targetistType = new TypeToken<List<ProjectDto>>(){}.getType();
        return modelMapper.map(projects, targetistType);
    }

    @Override
    public List<FullProjectDto> fromProjectsToFullProjectDtos(List<Project> projects) {

        List<FullProjectDto> projectDtos = new ArrayList<>();
        for (Project project : projects) {
            FullProjectDto projectDto = modelMapper.map(project, FullProjectDto.class);

            List<JiraUsersReferences> references = project.getReferences();
            List<JiraUser> users = new ArrayList<>();
            for (JiraUsersReferences usersReferences : references) {
                users.add(usersReferences.getUser());
            }

            List<FullProjectUserDto> userDtos = new ArrayList<>();
            for (JiraUser user : users) {
                FullProjectUserDto projectUserDto = modelMapper.map(user, FullProjectUserDto.class);
                projectUserDto.setEngineerLevel(user.getPosition() != null ? user.getPosition().getName() : "");
                if (user.getUserReferences().size() > 0) {
                    projectUserDto.setColumn(modelMapper.map(project, ProjectDto.class));
                }

                List<JiraUsersReferences> userRef = user.getUserReferences();
                List<ResourceColumn> columns = new ArrayList<>();

                for (JiraUsersReferences usersReferences : userRef) {
                    if (usersReferences.getProject().getId().equals(project.getId())) {
                        columns.add(0, usersReferences.getColumn());
                    } else {
                        columns.add(usersReferences.getColumn());
                    }
                }
//                Collections.sort(columns, (o1, o2) -> o1.getPriority() - o2.getPriority());

                Type targetistType = new TypeToken<List<ResourceColumnDto>>(){}.getType();

                projectUserDto.setAssignmentTypes(modelMapper.map(columns, targetistType));
                projectUserDto.setAvatar(user.getAvatar());

                userDtos.add(projectUserDto);
            }

            projectDto.setUsers(userDtos);

            projectDtos.add(projectDto);
        }

       return projectDtos;
    }

    @Override
    public FullProjectDto fromProjectToFullProjectDto(Project project) {
        return modelMapper.map(project, FullProjectDto.class);
    }
}
