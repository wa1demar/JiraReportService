package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintIssueMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintIssue;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Component
public class SprintIssueMapperImpl implements SprintIssueMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public SprintIssue fromDto(SprintIssueDto sprintIssueDto) {
        return modelMapper.map(sprintIssueDto, SprintIssue.class);
    }

    @Override
    public SprintIssueDto toDto(SprintIssue sprintIssue) {
        return modelMapper.map(sprintIssue, SprintIssueDto.class);
    }

    @Override
    public List<SprintIssue> fromDtos(List<SprintIssueDto> sprintIssueDtos) {
        Type targetistType = new TypeToken<List<SprintIssueDto>>(){}.getType();
        return modelMapper.map(sprintIssueDtos, targetistType);
    }

    @Override
    public List<SprintIssueDto> toDtos(List<SprintIssue> sprintIssues) {
        Type targetistType = new TypeToken<List<SprintIssueDto>>(){}.getType();
        return modelMapper.map(sprintIssues, targetistType);
    }

}
