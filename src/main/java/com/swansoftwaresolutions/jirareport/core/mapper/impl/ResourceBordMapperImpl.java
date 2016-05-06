package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceFilterData;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ResourceBordMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.propertymap.JiraUserToFullResourceUserDtoMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.propertymap.JiraUserToResourceUserDtoMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public List<FullResourceColumnDto> fromResourceColumnsToFullResourceColumnDtos(List<ResourceColumn> columns, ResourceFilterData filterData) {
        Collections.sort(columns, (o1, o2) -> o1.getPriority() - o2.getPriority());
        List<FullResourceColumnDto> columnDtos = new ArrayList<>();
        List<FullResourceUserDto> allUsersFiltered = new ArrayList<>();
        for (ResourceColumn c : columns) {
            FullResourceColumnDto fullResourceColumnDto = modelMapper.map(c, FullResourceColumnDto.class);
            if (c.getUsers() != null && c.getUsers().size() > 0) {
                List<FullResourceUserDto> fullResourceUserDtos = new ArrayList<>();
                for (JiraUser user : c.getUsers()) {
                    FullResourceUserDto userDto = modelMapper.map(user, FullResourceUserDto.class);
                    if (!allUsersFiltered.contains(userDto) && filterName(userDto, filterData.getName())
                            && filterTechnologies(userDto, filterData.getTechnology())
                            && filterLevel(userDto, filterData.getEngineerLevel())
                            && filterLocations(userDto, filterData.getLocation())) {
                        if (user.getColumns() != null) {
                            ResourceColumn column = user.getColumns().get(0);
                            userDto.setColumn(modelMapper.map(column, ResourceColumnDto.class));
                        }

                        fullResourceUserDtos.add(userDto);
                        allUsersFiltered.add(userDto);
                    }

                }
                Collections.sort(fullResourceUserDtos, (o1, o2) -> o1.getResourceOrder() - o2.getResourceOrder());
                fullResourceColumnDto.setUsers(fullResourceUserDtos);
            }
            columnDtos.add(fullResourceColumnDto);
        }
        Collections.sort(columnDtos, (o1, o2) -> o1.getSortPosition() - o2.getSortPosition());

        return columnDtos;
    }

    private boolean filterLocations(FullResourceUserDto userDto, Long[] locations) {
        if (locations == null) {
            return true;
        }

        for (int i = 0; i < locations.length; i++) {

            List<Long> filteredData = Arrays.stream(locations).filter(t -> t.equals(userDto.getLocation().getId())).collect(Collectors.toList());
            if (filteredData != null && filteredData.size() > 0) {
                return true;
            }
        }

        return false;

    }

    private boolean filterTechnologies(FullResourceUserDto userDto, Long[] technologies) {
        if (technologies == null) {
            return true;
        }

        for (int i = 0; i < technologies.length; i++) {
            int finalI = i;
            List<TechnologyDto> filteredData = userDto.getTechnologies().stream().filter(t -> t.getId().equals(Long.parseLong(String.valueOf(technologies[finalI])))).collect(Collectors.toList());
            if (filteredData != null && filteredData.size() > 0) {
                return true;
            }
        }

        return false;

    }

    private boolean filterName(FullResourceUserDto userDto, String name) {
        if (name == null) {
            return true;
        }
        String[] names = userDto.getName().split(" ");
        if (StringUtils.startsWithIgnoreCase(names[0], name) || (names.length > 1 && StringUtils.startsWithIgnoreCase(names[1], name))) {
            return true;
        }

        return false;
    }

    private boolean filterLevel(FullResourceUserDto userDto, Integer[] level) {
        if (level == null) {
            return true;
        }
        for (int i = 0; i < level.length; i++) {

            List<Integer> filteredData = Arrays.stream(level).filter(t -> t.equals(userDto.getEngineerLevel())).collect(Collectors.toList());
            if (filteredData != null && filteredData.size() > 0) {
                return true;
            }
        }

        return false;

    }


}
