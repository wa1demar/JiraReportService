package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.NewResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDtoList;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDtos;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ResourceBordService {
    ResourceColumnDto add(ResourceColumnDto columnDto);

    ResourceColumnDto update(ResourceColumnDto columnDto);

    FullResourceColumnDtoList getColumns();

    void deleteColumn(Long id);

    List<ResourceColumnDto> getColumnsList();

}
