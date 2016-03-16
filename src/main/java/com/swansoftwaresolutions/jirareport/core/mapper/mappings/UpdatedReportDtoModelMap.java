package com.swansoftwaresolutions.jirareport.core.mapper.mappings;

import com.swansoftwaresolutions.jirareport.core.dto.report.ReportDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class UpdatedReportDtoModelMap extends PropertyMap<ReportDto, Report> {
    @Override
    protected void configure() {
        map().setTargetUatDefectHours(null);
        map().setActualHours(null);
        map().setActualQatDefectHours(null);
        map().setActualUatDefectHours(null);
        map().setSyncDate(null);
        map().setTargetHours(null);
        map().setTargetQatDefectHours(null);
    }
}
