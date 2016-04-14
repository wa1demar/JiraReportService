package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;

/**
 * @author Vladimir Martynyuk
 */
public interface ResourceBordMapper {
    ResourceColumn fromResourceColumnDtoToresourceColumn(ResourceColumnDto columnDto);

    ResourceColumnDto fromResourceColumnToresourceColumnDto(ResourceColumn resourceColumn);
}
