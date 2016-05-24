package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullProjectUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.FullProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectFilterData;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<FullProjectDto> fromProjectsToFullProjectDtos(List<Project> projects, ProjectFilterData filterData) {

        List<FullProjectDto> projectDtos = new ArrayList<>();
        for (Project project : projects) {
            int count = 0;
            FullProjectDto projectDto = modelMapper.map(project, FullProjectDto.class);

            List<JiraUsersReferences> references = project.getReferences();
            List<JiraUser> users = new ArrayList<>();
            for (JiraUsersReferences usersReferences : references) {
                users.add(usersReferences.getUser());
            }

            List<FullProjectUserDto> userDtos = new ArrayList<>();
            for (JiraUser user : users) {

                FullProjectUserDto userDto = modelMapper.map(user, FullProjectUserDto.class);

                if (user.getUserReferences().size() > 0) {
                    userDto.setColumn(modelMapper.map(project, ProjectDto.class));
                }

                List<JiraUsersReferences> userRef = user.getUserReferences();
                List<ResourceColumn> columns = new ArrayList<>();
                List<ProjectDto> userProjects = new ArrayList<>();
                for (JiraUsersReferences usersReferences : userRef) {
                    if (usersReferences.getProject() != null) {
                        userProjects.add(modelMapper.map(usersReferences.getProject(), ProjectDto.class));
                    }
                    if (usersReferences.getProject().getId().equals(project.getId())) {
                        columns.add(0, usersReferences.getColumn());
                    } else {
                        columns.add(usersReferences.getColumn());
                    }
                }

                Type targetistType = new TypeToken<List<ResourceColumnDto>>(){}.getType();

                userDto.setAssignmentTypes(modelMapper.map(columns, targetistType));

                userDto.setProjects(userProjects);


                if (filterTechnologies(userDto, filterData.getTechnology())
                        && filterLevel(userDto, filterData.getEngineerLevel())
                        && filterProject(userDto, filterData.getProject())
                        && filterColumn(userDto, filterData.getAssignmentType())
                        && filterLocations(userDto, filterData.getLocation())) {

                    userDto.setAvatar(user.getAvatar());

                    userDtos.add(userDto);
                }

            }

            projectDto.setUsers(userDtos);

            projectDtos.add(projectDto);
        }

       return projectDtos;
    }

    private boolean filterColumn(FullProjectUserDto projectUserDto, Long[] assignmentTypes) {
        if (assignmentTypes == null || assignmentTypes.length == 0) {
            return true;
        }

        for (int i = 0; i < assignmentTypes.length; i++) {
            int finalI = i;
            List<ResourceColumnDto> filteredData = projectUserDto.getAssignmentTypes().stream().filter(t -> t.getId().equals(assignmentTypes[finalI])).collect(Collectors.toList());
            if (filteredData != null && filteredData.size() > 0) {
                return true;
            }
        }

        return false;

    }

    private boolean filterLocations(FullProjectUserDto projectUserDto, Long[] locations) {
        if (locations == null || locations.length == 0) {
            return true;
        }

        for (int i = 0; i < locations.length; i++) {

            List<Long> filteredData = Arrays.stream(locations).filter(t -> t.equals(projectUserDto.getLocation().getId())).collect(Collectors.toList());
            if (filteredData != null && filteredData.size() > 0) {
                return true;
            }
        }

        return false;
    }

    private boolean filterProject(FullProjectUserDto projectUserDto, Long[] projects) {
        if (projects == null || projects.length == 0) {
            return true;
        }

        for (int i = 0; i < projects.length; i++) {
            int finalI = i;
            List<ProjectDto> filteredData = projectUserDto.getProjects().stream().filter(t -> t.getId().equals(projects[finalI])).collect(Collectors.toList());
            if (filteredData != null && filteredData.size() > 0) {
                return true;
            }
        }

        return false;
    }

    private boolean filterLevel(FullProjectUserDto projectUserDto, Long[] engineerLevel) {
        if (engineerLevel == null || engineerLevel.length == 0) {
            return true;
        }

        if (projectUserDto.getPosition() == null) {
            return false;
        }

        for (int i = 0; i < engineerLevel.length; i++) {

            List<Long> filteredData = Arrays.stream(engineerLevel).filter(t -> t.equals(projectUserDto.getPosition().getId())).collect(Collectors.toList());
            if (filteredData != null && filteredData.size() > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public FullProjectDto fromProjectToFullProjectDto(Project project) {
        FullProjectDto fullProjectDto = modelMapper.map(project, FullProjectDto.class);

        List<JiraUsersReferences> references = project.getReferences();
        if (references.size() > 0) {
            List<FullProjectUserDto> users = new ArrayList<>();
            for (JiraUsersReferences references1 : references) {
                users.add(modelMapper.map(references1.getUser(), FullProjectUserDto.class));
            }

            fullProjectDto.setUsers(users);

        }
        return fullProjectDto;
    }


    private boolean filterTechnologies(FullProjectUserDto userDto, Long[] technologies) {
        if (technologies == null || technologies.length == 0) {
            return true;
        }

        for (int i = 0; i < technologies.length; i++) {
            int finalI = i;
            List<TechnologyDto> filteredData = userDto.getTechnologies().stream().filter(t -> t.getId().equals(Long.parseLong(String.valueOf(technologies[finalI])))).collect(Collectors.toList());
            if (filteredData != null && filteredData.size() > 0) {
                return true;
            }
        }

        return false;

    }
}
