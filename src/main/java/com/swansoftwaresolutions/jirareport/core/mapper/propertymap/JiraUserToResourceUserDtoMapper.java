package com.swansoftwaresolutions.jirareport.core.mapper.propertymap;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class JiraUserToResourceUserDtoMapper extends PropertyMap<JiraUser, ResourceUserDto> {
    @Override
    protected void configure() {
        map().setEngineerLevel(source.getLevel());
    }
}
