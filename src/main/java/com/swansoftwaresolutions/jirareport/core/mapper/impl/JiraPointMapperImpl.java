package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.JiraPointDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraPointMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraPoint;
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
public class JiraPointMapperImpl implements JiraPointMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public JiraPointDto toDto(JiraPoint report) {
        return modelMapper.map(report, JiraPointDto.class);
    }

    @Override
    public List<JiraPointDto> toDtos(List<JiraPoint> reportList) {
        Type targetistType = new TypeToken<List<JiraPointDto>>(){}.getType();
        return modelMapper.map(reportList, targetistType);
    }

    @Override
    public JiraPoint fromDto(JiraPointDto reportDto) {
        return modelMapper.map(reportDto, JiraPoint.class);
    }

    @Override
    public List<JiraPoint> fromDtos(List<JiraPointDto> reportDtos) {
        Type targetistType = new TypeToken<List<JiraPoint>>(){}.getType();
        return modelMapper.map(reportDtos, targetistType);
    }

}
