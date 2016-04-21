package com.swansoftwaresolutions.jirareport.core.mapper.propertymap;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUserDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class ImportedJiraUserDtoToJiraUserMapper extends PropertyMap<ImportedJiraUserDto, JiraUser> {
    @Override
    protected void configure() {
        map().setLogin(source.getName());
        map().setFullName(source.getDisplayName());
        map().setAvatar(source.getAvatarUrl());
    }
}
