package com.swansoftwaresolutions.jirareport.core.mapper.propertymap;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullProjectUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullResourceUserDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class JiraUserToFullProjectUserDtoMapper extends PropertyMap<JiraUser, FullProjectUserDto> {

    @Override
    protected void configure() {
        map().setEngineerLevel(source.getLevel());
    }

}
