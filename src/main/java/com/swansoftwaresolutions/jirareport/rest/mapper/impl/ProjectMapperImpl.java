package com.swansoftwaresolutions.jirareport.rest.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Project;
import com.swansoftwaresolutions.jirareport.rest.mapper.ProjectMapper;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ProjectDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */

@Component
public class ProjectMapperImpl implements ProjectMapper{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProjectDto toDto(Project projectEntity) {
        return modelMapper.map(projectEntity, ProjectDto.class);
    }

    @Override
    public List<ProjectDto> toDtos(List<Project> projectEntities) {
        Type targetistType = new TypeToken<List<ProjectDto>>(){}.getType();
        return modelMapper.map(projectEntities, targetistType);
    }

    @Override
    public Project fromDto(ProjectDto projectDto) {
        return modelMapper.map(projectDto, Project.class);
    }

    @Override
    public List<Project> fromDtos(ProjectDto projectDto) {
        Type targetistType = new TypeToken<List<Project>>(){}.getType();
        return modelMapper.map(projectDto, targetistType);
    }
}
