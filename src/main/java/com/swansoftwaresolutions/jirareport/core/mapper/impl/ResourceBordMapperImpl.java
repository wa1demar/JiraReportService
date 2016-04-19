package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ResourceBordMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.propertymap.JiraUserToFullResourceUserDtoMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.propertymap.JiraUserToResourceUserDtoMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
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
public class ResourceBordMapperImpl implements ResourceBordMapper {

    ModelMapper modelMapper;

    @Autowired
    public ResourceBordMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new JiraUserToResourceUserDtoMapper());
        modelMapper.addMappings(new JiraUserToFullResourceUserDtoMapper());
    }

    @Override
    public ResourceColumn fromResourceColumnDtoToResourceColumn(ResourceColumnDto columnDto) {
        return modelMapper.map(columnDto, ResourceColumn.class);
    }

    @Override
    public ResourceColumnDto fromResourceColumnToResourceColumnDto(ResourceColumn resourceColumn) {
        return modelMapper.map(resourceColumn, ResourceColumnDto.class);
    }

    @Override
    public List<FullResourceColumnDto> fromResourceColumnsToFullResourceColumnDtos(List<ResourceColumn> columns) {
        Type targetistType = new TypeToken<List<FullResourceColumnDto>>(){}.getType();
        return modelMapper.map(columns, targetistType);
    }
}
