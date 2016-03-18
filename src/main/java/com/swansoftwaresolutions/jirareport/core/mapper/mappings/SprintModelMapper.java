package com.swansoftwaresolutions.jirareport.core.mapper.mappings;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class SprintModelMapper  extends PropertyMap<Sprint, SprintDto> {
    @Override
    protected void configure() {
        map().setReportId(source.getReport().getId());
    }
}
