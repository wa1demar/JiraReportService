package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.mapper.JiraIssueMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraIssueDto;
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
public class JiraIssueMapperImpl implements JiraIssueMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public JiraIssueDto toDto(JiraIssue jiraIssue) {
        return modelMapper.map(jiraIssue, JiraIssueDto.class);
    }

    @Override
    public List<JiraIssueDto> toDtos(List<JiraIssue> jiraIssueList) {
        Type targetistType = new TypeToken<List<JiraIssueDto>>() {
        }.getType();
        return modelMapper.map(jiraIssueList, targetistType);
    }

    @Override
    public JiraIssue fromDto(JiraIssueDto jiraIssueDto) {
        return modelMapper.map(jiraIssueDto, JiraIssue.class);
    }

    @Override
    public List<JiraIssue> fromDtos(List<JiraIssueDto> jiraIssueDtoList) {
        Type targetistType = new TypeToken<List<JiraIssue>>() {
        }.getType();
        return modelMapper.map(jiraIssueDtoList, targetistType);
    }

}
