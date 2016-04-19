package com.swansoftwaresolutions.jirareport.core.mapper.propertymap;

import com.swansoftwaresolutions.jirareport.core.dto.report.NewReportDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Report;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class NewReportDtoModelMap extends PropertyMap<NewReportDto, Report> {

    @Override
    protected void configure() {
        map().setId(null);
//        map().setTitle(source.getTitle());
//        map().setCreator(source.getCreator());
//        map().setBoardId(source.getBoardId());
//        map().setTypeId(source.getTypeId());
        map().setAdmins(null);
    }


}
