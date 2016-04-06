package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.ImportedBardsDto;
import com.swansoftwaresolutions.jirareport.core.dto.ImportedJiraBoardDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardInfoDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraBoardMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.mappings.ImportedJiraBoardDtoMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
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

    private ModelMapper modelMapper;

    @Autowired
    public JiraBoardMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new ImportedJiraBoardDtoMapper());
    }

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
        Type targetistType = new TypeToken<List<JiraProject>>() {
        }.getType();
        return modelMapper.map(jiraBoardDtoList, targetistType);
    }

    @Override
    public List<JiraBoard> fromImportedDtos(List<ImportedJiraBoardDto> importedJiraBoardDtos) {
        Type targetistType = new TypeToken<List<JiraBoard>>() {
        }.getType();
        return modelMapper.map(importedJiraBoardDtos, targetistType);
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
        Type targetistType = new TypeToken<List<JiraProject>>() {
        }.getType();
        return modelMapper.map(jiraBoardInfoDtoList, targetistType);
    }
}
