package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.TechnologyMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.propertymap.ImportedJiraUserDtoToJiraUserMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
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
//        if (jiraUser.getColumns() != null && jiraUser.getColumns().size() > 0) {
//            userDto.setColumn(modelMapper.map(jiraUser.getColumns().get(0), ResourceColumnDto.class));
//        } else {
//            userDto.setColumn(null);
//        }
        return userDto;
    }


}
