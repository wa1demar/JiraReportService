package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.NewResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDtoList;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.ResourceBordMapper;
import com.swansoftwaresolutions.jirareport.core.service.ResourceBordService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ResourceBordRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.TechnologyRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class ResourceBordServiceImpl implements ResourceBordService {

    @Autowired
    ResourceBordRepository resourceBordRepository;

    @Autowired
    JiraUserRepository jiraUserRepository;

    @Autowired
    TechnologyRepository technologyRepository;

    @Autowired
    ResourceBordMapper resourceBordMapper;

    @Autowired
    JiraUserMapper jiraUserMapper;

    @Override
    public ResourceColumnDto add(ResourceColumnDto columnDto) {
        ResourceColumn resourceColumn = resourceBordRepository.add(resourceBordMapper.fromResourceColumnDtoToresourceColumn(columnDto));

        return resourceBordMapper.fromResourceColumnToResourceColumnDto(resourceColumn);
    }

    @Override
    public ResourceColumnDto update(ResourceColumnDto columnDto) {
        ResourceColumn resourceColumn = resourceBordRepository.update(resourceBordMapper.fromResourceColumnDtoToresourceColumn(columnDto));

        return resourceBordMapper.fromResourceColumnToResourceColumnDto(resourceColumn);
    }

    @Override
    public FullResourceColumnDtoList getColumns() {
        List<ResourceColumn> columns = resourceBordRepository.findAll();
        List<FullResourceColumnDto> columnDtos = resourceBordMapper.fromResourceColumnsToFullResourceColumnDtos(columns);
        FullResourceColumnDtoList fullResourceColumnDtoList = new FullResourceColumnDtoList();
        fullResourceColumnDtoList.setColumns(columnDtos);

        return fullResourceColumnDtoList;
    }

    @Override
    public void deleteColumn(Long id) {
        List<JiraUser> users = resourceBordRepository.findUsersByColumnId(id);
        resourceBordRepository.moveUsersToDefaultColumn(users);
        resourceBordRepository.removeColumn(id);
    }
}
