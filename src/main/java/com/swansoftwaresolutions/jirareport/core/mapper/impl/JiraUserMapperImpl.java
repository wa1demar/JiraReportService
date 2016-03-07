package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraUserAutoDto;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraUserSchedulerDto;
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
public class JiraUserMapperImpl implements JiraUserMapper {

    @Autowired
    private ModelMapper modelMapper;

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
}
