package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraProjectMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraProjectDto;
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
public class JiraProjectMapperImpl implements JiraProjectMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public JiraProjectDto toDto(JiraProject jiraProjectEntity) {
        return modelMapper.map(jiraProjectEntity, JiraProjectDto.class);
    }

    @Override
    public List<JiraProjectDto> toDtos(List<JiraProject> jiraProjectEntities) {
        Type targetistType = new TypeToken<List<JiraProjectDto>>() {
        }.getType();
        return modelMapper.map(jiraProjectEntities, targetistType);
    }

    @Override
    public JiraProject fromDto(JiraProjectDto jiraProjectDto) {
        return modelMapper.map(jiraProjectDto, JiraProject.class);
    }

    @Override
    public List<JiraProject> fromDtos(JiraProjectDto jiraProjectDto) {
        Type targetistType = new TypeToken<List<JiraProject>>() {
        }.getType();
        return modelMapper.map(jiraProjectDto, targetistType);
    }

    @Override
    public List<JiraProject> fromDtos(List<ImportedProjectDto> importedProjectsDtos) {
        Type targetistType = new TypeToken<List<JiraProject>>() {
        }.getType();
        return modelMapper.map(importedProjectsDtos, targetistType);
    }
}
