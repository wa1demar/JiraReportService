package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ResourceBordMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class ResourceBordMapperImpl implements ResourceBordMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResourceColumn fromResourceColumnDtoToresourceColumn(ResourceColumnDto columnDto) {
        return modelMapper.map(columnDto, ResourceColumn.class);
    }

    @Override
    public ResourceColumnDto fromResourceColumnToresourceColumnDto(ResourceColumn resourceColumn) {
        return modelMapper.map(resourceColumn, ResourceColumnDto.class);
    }
}
