package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.propertymap.ImportedJiraUsersMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.propertymap.JiraUserToResourceUserDtoMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserAutoDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Vitaliy Hollovko
 * @author Vladimir Martynyuk
 */
@Component
public class JiraUserMapperImpl implements JiraUserMapper {

    private ModelMapper modelMapper;

    @Autowired
    public JiraUserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.addMappings(new ImportedJiraUsersMapper());
        modelMapper.addMappings(new JiraUserToResourceUserDtoMapper());
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
    public JiraUserAutoDto toAutoDto(JiraUser jiraUser) {
        return modelMapper.map(jiraUser, JiraUserAutoDto.class);
    }

    @Override
    public List<JiraUserAutoDto> toAutoDtos(List<JiraUser> jiraUser) {
        Type targetistType = new TypeToken<List<JiraUserAutoDto>>() {
        }.getType();
        return modelMapper.map(jiraUser, targetistType);
    }

    @Override
    public JiraUser fromAutoDto(JiraUserAutoDto jiraUserAutoDto) {
        return modelMapper.map(jiraUserAutoDto, JiraUser.class);
    }

    @Override
    public List<JiraUser> fromAutoDtos(List<JiraUserAutoDto> jiraUserAutoDtoList) {
        Type targetistType = new TypeToken<List<JiraUser>>() {
        }.getType();
        return modelMapper.map(jiraUserAutoDtoList, targetistType);
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
        return userDto;
    }


}
