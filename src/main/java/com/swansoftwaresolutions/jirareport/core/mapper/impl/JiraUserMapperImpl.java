package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.TechnologyMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.propertymap.ImportedJiraUserDtoToJiraUserMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUsersReferences;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Vitaliy Hollovko
 * @author Vladimir Martynyuk
 */
@Component
public class JiraUserMapperImpl implements JiraUserMapper {

    private ModelMapper modelMapper;
    private TechnologyMapper technologyMapper;

    @Autowired
    public JiraUserMapperImpl(ModelMapper modelMapper, TechnologyMapper technologyMapper) {
        this.modelMapper = modelMapper;
        this.technologyMapper = technologyMapper;
        modelMapper.addMappings(new ImportedJiraUserDtoToJiraUserMapper());
    }

    @Override
    public JiraUserDto toDto(JiraUser jiraUserEntity) {
        return modelMapper.map(jiraUserEntity, JiraUserDto.class);
    }

    @Override
    public List<JiraUserDto> toDtos(List<JiraUser> jiraUserEntities) {
        Type targetistType = new TypeToken<List<JiraUserDto>>() {
        }.getType();
        return modelMapper.map(jiraUserEntities, targetistType);
    }

    @Override
    public JiraUser fromDto(JiraUserDto jiraUserDto) {
        return modelMapper.map(jiraUserDto, JiraUser.class);
    }

    @Override
    public List<JiraUser> fromDtos(JiraUserDto jiraUserDto) {
        Type targetistType = new TypeToken<List<JiraUser>>() {
        }.getType();
        return modelMapper.map(jiraUserDto, targetistType);
    }

    @Override
    public List<JiraUser> fromDtos(List<ImportedJiraUserDto> usersList) {
        Type targetistType = new TypeToken<List<JiraUser>>() {
        }.getType();
        return modelMapper.map(usersList, targetistType);
    }

    @Override
    public ResourceUserDto fromJiraUserToResourceUserDto(JiraUser jiraUser) {
        ResourceUserDto userDto = modelMapper.map(jiraUser, ResourceUserDto.class);
        userDto.setTechnologies(technologyMapper.fromTechnologiesToTechnologiesDto(jiraUser.getTechnologies()));
        if (jiraUser.getUserReferences() != null && jiraUser.getUserReferences().size() > 0) {
            List<JiraUsersReferences> userReferences = jiraUser.getUserReferences();
            List<ResourceColumn> resourceColumns = new ArrayList<>();
            List<Project> projects = new ArrayList<>();
            Map<Long, ResourceColumn> columnMap = new HashMap<>();
            for (JiraUsersReferences r : userReferences) {
                resourceColumns.add(r.getColumn());
                if (r.getProject() != null) {
                    Project project = r.getProject();
                    columnMap.put(project.getId(), r.getColumn());
                    projects.add(project);
                }
            }
            Collections.sort(resourceColumns, (o1, o2) -> o1.getPriority() - o2.getPriority());

            ResourceColumn column = resourceColumns.get(0);
            userDto.setColumn(modelMapper.map(column, ResourceColumnDto.class));
//            if (jiraUser.getPosition() != null) {
//                userDto.setPositionDto(modelMapper.map(jiraUser.getPosition(), PositionDto.class));
//            }

            if (projects != null && projects.size() > 0) {
                List<ProjectDto> resultProjects = new ArrayList<>();
                for (Project pr : projects) {
                    ProjectDto projectDto = modelMapper.map(pr, ProjectDto.class);
                    projectDto.setAssignmentType(modelMapper.map(columnMap.get(pr.getId()), ResourceColumnDto.class));

                    resultProjects.add(projectDto);
                }
                userDto.setProjects(resultProjects);
            }
        } else {
            userDto.setColumn(null);
        }

        return userDto;
    }

    @Override
    public com.swansoftwaresolutions.jirareport.core.dto.jira_users.JiraUserDto fromJiraUserToJiraUserDto(JiraUser user) {
        return modelMapper.map(user, com.swansoftwaresolutions.jirareport.core.dto.jira_users.JiraUserDto.class);
    }


}
