package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDtos;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ResourceBordMapper {
    ResourceColumn fromResourceColumnDtoToResourceColumn(ResourceColumnDto columnDto);

    ResourceColumnDto fromResourceColumnToResourceColumnDto(ResourceColumn resourceColumn);

    List<FullResourceColumnDto> fromResourceColumnsToFullResourceColumnDtos(List<ResourceColumn> columns);

    List<ResourceColumnDto> fromResourceColumnsToResourceColumnDtos(List<ResourceColumn> columns);
}
