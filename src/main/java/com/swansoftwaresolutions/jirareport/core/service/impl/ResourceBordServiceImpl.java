package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.*;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.ResourceBordMapper;
import com.swansoftwaresolutions.jirareport.core.service.ResourceBordService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUsersReferences;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUsersReferencesRepository;
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
    JiraUsersReferencesRepository jiraUsersReferencesRepository;

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
        ResourceColumn resourceColumn = resourceBordRepository.add(resourceBordMapper.fromResourceColumnDtoToResourceColumn(columnDto));

        return resourceBordMapper.fromResourceColumnToResourceColumnDto(resourceColumn);
    }

    @Override
    public ResourceColumnDto update(ResourceColumnDto columnDto) {
        ResourceColumn resourceColumn = resourceBordRepository.update(resourceBordMapper.fromResourceColumnDtoToResourceColumn(columnDto));

        return resourceBordMapper.fromResourceColumnToResourceColumnDto(resourceColumn);
    }

    @Override
    public FullResourceColumnDtoList getColumns() {
        List<ResourceColumn> columns = resourceBordRepository.findAll(false);
        List<FullResourceColumnDto> columnDtos = resourceBordMapper.fromResourceColumnsToFullResourceColumnDtos(columns);
        FullResourceColumnDtoList fullResourceColumnDtoList = new FullResourceColumnDtoList();
        fullResourceColumnDtoList.setColumns(columnDtos);

        return fullResourceColumnDtoList;
    }

    @Override
    public void deleteColumn(Long id) throws NoSuchEntityException {
        List<JiraUser> users = resourceBordRepository.findUsersByColumnId(id);

        for (JiraUser u : users) {
            jiraUsersReferencesRepository.deleteByAssignmentType(u.getLogin(), id);

            JiraUser updatedUser = jiraUserRepository.findByLogin(u.getLogin());
            if (updatedUser.getUserReferences().size() == 0) {
                ResourceColumn defaultColumn = resourceBordRepository.findDefaultColumn();
                JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
                jiraUsersReferences.setUser(updatedUser);
                jiraUsersReferences.setColumn(defaultColumn);
                jiraUsersReferencesRepository.add(jiraUsersReferences);

            }
        }

        resourceBordRepository.removeColumn(id);
    }

    @Override
    public List<ResourceColumnDto> getColumnsList(boolean sorted) {
        return resourceBordMapper.fromResourceColumnsToResourceColumnDtos(resourceBordRepository.findAll(sorted));
    }

    @Override
    public List<ResourceColumnDto> updatePriority(ResourceColumnPriority[] columnPriorities) {
        resourceBordRepository.updatePriorities(columnPriorities);

        return getColumnsList(true);
    }

    @Override
    public FullResourceColumnDtoList getColumns(ResourceFilterData filterData) {
        List<ResourceColumn> columns = resourceBordRepository.findAll(true);
        List<FullResourceColumnDto> columnDtos = resourceBordMapper.fromResourceColumnsToFullResourceColumnDtos(columns, filterData);
        FullResourceColumnDtoList fullResourceColumnDtoList = new FullResourceColumnDtoList();
        fullResourceColumnDtoList.setColumns(columnDtos);

        return fullResourceColumnDtoList;
    }

    @Override
    public FullResourceColumnDtoList sort(SortingColumnsObject columnPriorities) {
        resourceBordRepository.sort(columnPriorities.getItems());

        return getColumns(columnPriorities.getFilters());
    }
}
