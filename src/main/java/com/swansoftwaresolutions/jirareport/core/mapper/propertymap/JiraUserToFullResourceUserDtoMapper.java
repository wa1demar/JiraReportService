package com.swansoftwaresolutions.jirareport.core.mapper.propertymap;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullResourceUserDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import org.modelmapper.Condition;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

import java.util.ArrayList;

/**
 * @author Vladimir Martynyuk
 */
public class JiraUserToFullResourceUserDtoMapper extends PropertyMap<JiraUser, FullResourceUserDto> {

    @Override
    protected void configure() {
        map().setEngineerLevel(source.getLevel());
    }

}
