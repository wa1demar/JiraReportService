package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.ImportedJiraSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.JiraSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.JiraSprintsDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraSprintMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.mappings.ImportedJiraSprintDtoMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;
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
public class JiraSprintMapperImpl implements JiraSprintMapper {

    ModelMapper modelMapper;

    @Autowired
    public JiraSprintMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.addMappings(new ImportedJiraSprintDtoMapper());
    }

    @Override
    public List<JiraSprint> fromDtos(List<JiraSprintDto> jiraSprintDtos) {
        return null;
    }

    @Override
    public List<JiraSprint> fromShedullerDtos(List<ImportedJiraSprintDto> sprintDtos) {
        Type targetistType = new TypeToken<List<JiraSprint>>(){}.getType();
        return modelMapper.map(sprintDtos, targetistType);
    }

    @Override
    public JiraSprintsDto toDto(List<JiraSprint> jiraSprints) {
        JiraSprintsDto jiraSprintsDto = new JiraSprintsDto();
        Type targetistType = new TypeToken<List<JiraSprintDto>>(){}.getType();
        jiraSprintsDto.setSprints(modelMapper.map(jiraSprints, targetistType));

        return jiraSprintsDto;
    }

    @Override
    public JiraSprintDto toDto(JiraSprint jiraSprint) {
        return modelMapper.map(jiraSprint, JiraSprintDto.class);
    }

    @Override
    public JiraSprint fromDto(JiraSprintDto sprint) {
        return modelMapper.map(sprint, JiraSprint.class);
    }

    @Override
    public JiraSprint fromDto(ImportedJiraSprintDto sprint) {
        return modelMapper.map(sprint, JiraSprint.class);
    }

    @Override
    public List<ImportedJiraSprintDto> toDtos(List<JiraSprint> all) {
        Type targetistType = new TypeToken<List<ImportedJiraSprintDto>>(){}.getType();
        return modelMapper.map(all, targetistType);
    }
}
