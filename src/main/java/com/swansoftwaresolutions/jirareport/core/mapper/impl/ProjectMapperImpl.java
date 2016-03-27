package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ProjectMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ProjectDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Vitaliy Hollovko
 */

@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProjectDto toDto(JiraProject jiraProjectEntity) {
        return modelMapper.map(jiraProjectEntity, ProjectDto.class);
    }

    @Override
    public List<ProjectDto> toDtos(List<JiraProject> jiraProjectEntities) {
        Type targetistType = new TypeToken<List<ProjectDto>>() {
        }.getType();
        return modelMapper.map(jiraProjectEntities, targetistType);
    }

    @Override
    public JiraProject fromDto(ProjectDto projectDto) {
        return modelMapper.map(projectDto, JiraProject.class);
    }

    @Override
    public List<JiraProject> fromDtos(ProjectDto projectDto) {
        Type targetistType = new TypeToken<List<JiraProject>>() {
        }.getType();
        return modelMapper.map(projectDto, targetistType);
    }

    @Override
    public List<JiraProject> fromDtos(List<ImportedProjectDto> importedProjectsDtos) {
        Type targetistType = new TypeToken<List<JiraProject>>() {
        }.getType();
        return modelMapper.map(importedProjectsDtos, targetistType);
    }
}
