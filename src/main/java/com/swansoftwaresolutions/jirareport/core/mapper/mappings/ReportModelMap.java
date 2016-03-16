package com.swansoftwaresolutions.jirareport.core.mapper.mappings;

import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class ReportModelMap extends PropertyMap<Report, ReportDto> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setCreator(source.getCreator());
        map().setTypeId(source.getTypeId());
        map().setBoardId(source.getBoardId());
    }
}
