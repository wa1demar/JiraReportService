package com.swansoftwaresolutions.jirareport.core.mapper.propertymap;

import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */

public class SprintDeveloperDtoMapper extends PropertyMap<SprintDeveloper, SprintDeveloperDto> {

    @Override
    protected void configure() {

        map().setDeveloperLogin(source.getJiraUser().getLogin());
        map().setDeveloperName(source.getJiraUser().getFullName());

    }
}
