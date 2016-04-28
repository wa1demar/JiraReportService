package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ResourceBordMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.propertymap.JiraUserToFullResourceUserDtoMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.propertymap.JiraUserToResourceUserDtoMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class ResourceBordMapperImpl implements ResourceBordMapper {

    ModelMapper modelMapper;

    @Autowired
    public ResourceBordMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new JiraUserToResourceUserDtoMapper());
        modelMapper.addMappings(new JiraUserToFullResourceUserDtoMapper());
    }

    @Override
    public ResourceColumn fromResourceColumnDtoToResourceColumn(ResourceColumnDto columnDto) {
        return modelMapper.map(columnDto, ResourceColumn.class);
    }

    @Override
    public ResourceColumnDto fromResourceColumnToResourceColumnDto(ResourceColumn resourceColumn) {
        return modelMapper.map(resourceColumn, ResourceColumnDto.class);
    }

    @Override
    public List<FullResourceColumnDto> fromResourceColumnsToFullResourceColumnDtos(List<ResourceColumn> columns) {
        Collections.sort(columns, (o1, o2) -> o1.getPriority() - o2.getPriority());
        List<FullResourceColumnDto> columnDtos = new ArrayList<>();
        List<FullResourceUserDto> allUsersFiltered = new ArrayList<>();
        for (ResourceColumn c : columns) {
            FullResourceColumnDto fullResourceColumnDto = modelMapper.map(c, FullResourceColumnDto.class);
            if (c.getUsers() != null && c.getUsers().size() > 0) {
                List<FullResourceUserDto> fullResourceUserDtos = new ArrayList<>();
                for (JiraUser user : c.getUsers()) {
                    FullResourceUserDto userDto = modelMapper.map(user, FullResourceUserDto.class);
                    if (!allUsersFiltered.contains(userDto)) {
                        if (user.getColumns() != null) {
                            ResourceColumn column = user.getColumns().get(0);
                            userDto.setColumn(modelMapper.map(column, ResourceColumnDto.class));
                        }

                        fullResourceUserDtos.add(userDto);
                        allUsersFiltered.add(userDto);
                    }

                }
                fullResourceColumnDto.setUsers(fullResourceUserDtos);
            }
            columnDtos.add(fullResourceColumnDto);
        }
        Collections.sort(columnDtos, (o1, o2) -> o1.getId().compareTo(o2.getId()));

        return columnDtos;
    }

    @Override
    public List<ResourceColumnDto> fromResourceColumnsToResourceColumnDtos(List<ResourceColumn> columns) {
        Type targetistType = new TypeToken<List<ResourceColumnDto>>() {
        }.getType();
        return modelMapper.map(columns, targetistType);
    }

}
