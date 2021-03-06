package com.swansoftwaresolutions.jirareport.core.mapper.propertymap;

import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class UpdatedReportDtoModelMap extends PropertyMap<ReportDto, Report> {
    @Override
    protected void configure() {
        map().setSyncDate(null);

    }
}
