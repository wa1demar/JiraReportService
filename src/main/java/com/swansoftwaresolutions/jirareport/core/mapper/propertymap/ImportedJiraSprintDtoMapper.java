package com.swansoftwaresolutions.jirareport.core.mapper.propertymap;

import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.ImportedJiraSprintDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class ImportedJiraSprintDtoMapper extends PropertyMap<ImportedJiraSprintDto, JiraSprint> {
    @Override
    protected void configure() {
        map().setBoardId(source.getOriginBoardId());
    }
}
