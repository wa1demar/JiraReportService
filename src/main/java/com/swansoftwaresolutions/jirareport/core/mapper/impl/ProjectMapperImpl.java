package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDtos;
import com.swansoftwaresolutions.jirareport.core.mapper.ProjectMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
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
}
