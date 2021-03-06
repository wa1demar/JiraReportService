package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.FullResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceFilterData;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ResourceBordMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUsersReferences;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class ResourceBordMapperImpl implements ResourceBordMapper {

    ModelMapper modelMapper;

    @Autowired
    public ResourceBordMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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
            List<JiraUsersReferences> references = c.getReferences();
            int count = 0;
            if (references != null && references.size() > 0) {

                List<JiraUser> users = new ArrayList<>();
                for (JiraUsersReferences r : references) {
                    users.add(r.getUser());
                }
                List<FullResourceUserDto> fullResourceUserDtos = new ArrayList<>();
                for (JiraUser user : users) {
                    FullResourceUserDto userDto = modelMapper.map(user, FullResourceUserDto.class);
                    if (!allUsersFiltered.contains(userDto)) {
                        count++;
                    }
                    if (!allUsersFiltered.contains(userDto) && filterName(userDto, filterData.getName())
                            && filterTechnologies(userDto, filterData.getTechnology())
                            && filterLevel(userDto, filterData.getEngineerLevel())
                            && filterProject(userDto, filterData.getProject())
                            && filterLocations(userDto, filterData.getLocation())) {
                        if (user.getUserReferences() != null) {
                            List<ResourceColumn> resourceColumns = new ArrayList<>();
                            List<Project> projects = new ArrayList<>();
                            Map<Long, ResourceColumn> columnMap = new HashMap<>();
                            for (JiraUsersReferences r : user.getUserReferences()) {
                                resourceColumns.add(r.getColumn());
                                if (r.getProject() != null) {
                                    Project project = r.getProject();
                                    columnMap.put(project.getId(), r.getColumn());
                                    projects.add(project);
                                }
                            }
                            Collections.sort(resourceColumns, (o1, o2) -> o1.getPriority() - o2.getPriority());
                            ResourceColumn column = resourceColumns.get(0);
                            userDto.setColumn(modelMapper.map(column, ResourceColumnDto.class));

                            List<ProjectDto> resultProjects = new ArrayList<>();
                            for (Project pr : projects) {
                                ProjectDto projectDto = modelMapper.map(pr, ProjectDto.class);
                                projectDto.setAssignmentType(modelMapper.map(columnMap.get(pr.getId()), ResourceColumnDto.class));

                                resultProjects.add(projectDto);
                            }
                            userDto.setProjects(resultProjects);
                        }

                        allUsersFiltered.add(userDto);

                        fullResourceUserDtos.add(userDto);

                    }
                }
                Collections.sort(fullResourceUserDtos, (o1, o2) -> o1.getResourceOrder() - o2.getResourceOrder());
                fullResourceColumnDto.setUsers(fullResourceUserDtos);
                fullResourceColumnDto.setAllMembersCount(count);
                fullResourceColumnDto.setMembersCount(fullResourceUserDtos.size());

            }

            columnDtos.add(fullResourceColumnDto);
        }
        Collections.sort(columnDtos, (o1, o2) -> o1.getSortPosition() - o2.getSortPosition());

        return columnDtos;
    }

    private boolean filterLocations(FullResourceUserDto userDto, Long[] locations) {
        if (locations == null || locations.length == 0) {
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

    private boolean filterProject(FullResourceUserDto userDto, Long[] projects) {
        if (projects == null || projects.length == 0) {
            return true;
        }

        for (int i = 0; i < projects.length; i++) {

            List<Long> filteredData = Arrays.stream(projects).filter(t -> t.equals(userDto.getProjects())).collect(Collectors.toList());
            if (filteredData != null && filteredData.size() > 0) {
                return true;
            }
        }

        return false;

    }

    private boolean filterTechnologies(FullResourceUserDto userDto, Long[] technologies) {
        if (technologies == null || technologies.length == 0) {
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
        if (name == null || name.equals("")) {
            return true;
        }
        String[] names = userDto.getName().split(" ");
        if (StringUtils.startsWithIgnoreCase(names[0], name) || (names.length > 1 && StringUtils.startsWithIgnoreCase(names[1], name))) {
            return true;
        }

        return false;
    }

    private boolean filterLevel(FullResourceUserDto userDto, Long[] level) {
        if (level == null || level.length == 0) {
            return true;
        }

        if (userDto.getPosition() == null) {
            return false;
        }

        for (int i = 0; i < level.length; i++) {

            List<Long> filteredData = Arrays.stream(level).filter(t -> t.equals(userDto.getPosition().getId())).collect(Collectors.toList());
            if (filteredData != null && filteredData.size() > 0) {
                return true;
            }
        }

        return false;

    }


}
