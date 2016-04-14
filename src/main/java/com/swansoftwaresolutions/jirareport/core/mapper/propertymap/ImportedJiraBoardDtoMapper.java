package com.swansoftwaresolutions.jirareport.core.mapper.propertymap;

import com.swansoftwaresolutions.jirareport.core.dto.ImportedJiraBoardDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class ImportedJiraBoardDtoMapper  extends PropertyMap<ImportedJiraBoardDto, JiraBoard> {
    @Override
    protected void configure() {
        map().setBoardId(source.getId());
    }
}
