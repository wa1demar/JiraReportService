package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.core.entity.Project;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraBoardMapper;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraBoardInfoDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraBoardDto;
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
public class JiraBoardMapperImpl implements JiraBoardMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public JiraBoardDto toDto(JiraBoard jiraBoard) {
        return modelMapper.map(jiraBoard, JiraBoardDto.class);
    }

    @Override
    public List<JiraBoardDto> toDtos(List<JiraBoard> jiraBoardList) {
        Type targetistType = new TypeToken<List<JiraBoardDto>>() {
        }.getType();
        return modelMapper.map(jiraBoardList, targetistType);
    }

    @Override
    public JiraBoard fromDto(JiraBoardDto jiraBoardDto) {
        return modelMapper.map(jiraBoardDto, JiraBoard.class);
    }

    @Override
    public List<JiraBoard> fromDtos(List<JiraBoardDto> jiraBoardDtoList) {
        Type targetistType = new TypeToken<List<Project>>() {
        }.getType();
        return modelMapper.map(jiraBoardDtoList, targetistType);
    }

    @Override
    public JiraBoardInfoDto toInfoDto(JiraBoard jiraBoard) {
        return modelMapper.map(jiraBoard, JiraBoardInfoDto.class);
    }

    @Override
    public List<JiraBoardInfoDto> toInfoDtos(List<JiraBoard> jiraBoardList) {
        Type targetistType = new TypeToken<List<JiraBoardInfoDto>>() {
        }.getType();
        return modelMapper.map(jiraBoardList, targetistType);
    }

    @Override
    public JiraBoard fromInfoDto(JiraBoardInfoDto jiraBoardInfoDto) {
        return modelMapper.map(jiraBoardInfoDto, JiraBoard.class);
    }

    @Override
    public List<JiraBoard> fromInfoDtos(List<JiraBoardInfoDto> jiraBoardInfoDtoList) {
        Type targetistType = new TypeToken<List<Project>>() {
        }.getType();
        return modelMapper.map(jiraBoardInfoDtoList, targetistType);
    }
}
