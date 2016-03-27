package com.swansoftwaresolutions.jirareport.core.mapper.mappings;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUserDto;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import org.modelmapper.PropertyMap;

/**
 * @author Vladimir Martynyuk
 */
public class ImportedJiraUsersMapper extends PropertyMap<ImportedJiraUserDto, JiraUser> {
    @Override
    protected void configure() {
        map().setLogin(source.getName());
        map().setFullName(source.getDisplayName());
    }
}
