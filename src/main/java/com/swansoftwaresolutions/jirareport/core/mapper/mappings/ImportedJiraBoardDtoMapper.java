package com.swansoftwaresolutions.jirareport.core.mapper.mappings;

import com.swansoftwaresolutions.jirareport.core.dto.ImportedJiraBoardDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraBoard;
import org.modelmapper.PropertyMap;

/**
 * Created by waldemar on 4/6/16.
 */
public class ImportedJiraBoardDtoMapper  extends PropertyMap<ImportedJiraBoardDto, JiraBoard> {
    @Override
    protected void configure() {
        map().setBoardId(source.getId());
    }
}
