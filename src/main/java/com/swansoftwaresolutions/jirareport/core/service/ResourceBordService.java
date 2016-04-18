package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.NewResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

/**
 * @author Vladimir Martynyuk
 */
public interface ResourceBordService {
    ResourceColumnDto add(ResourceColumnDto columnDto);

    ResourceColumnDto update(ResourceColumnDto columnDto);

    ResourceUserDto addUserToBoard(NewResourceUserDto newResourceUserDto) throws NoSuchEntityException;
}
