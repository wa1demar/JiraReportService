package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.*;
import com.swansoftwaresolutions.jirareport.core.service.ResourceBordService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@RestController
public class ResourceBoardController {

    @Autowired
    ResourceBordService resourceBordService;

    @RequestMapping(value = "/rest/v1/resource_columns", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResourceColumnDto> addColumn(@Valid @RequestBody ResourceColumnDto columnDto) {

        ResourceColumnDto newLocationDto = resourceBordService.add(columnDto);

        return new ResponseEntity<>(newLocationDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/resource_columns/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResourceColumnDto> updateColumn(@PathVariable("id") Long id, @Valid @RequestBody ResourceColumnDto columnDto) {

        columnDto.setId(id);
        ResourceColumnDto newLocationDto = resourceBordService.update(columnDto);

        return new ResponseEntity<>(newLocationDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/resource_columns", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<FullResourceColumnDtoList> getColumnsWithUsersFiltered(@RequestParam(value = "technology", required = false) String[] technologies,
                                                                                 @RequestParam(value = "project", required = false) String[] projects,
                                                                                 @RequestParam(value = "engineerLevel", required = false) String[] engineerLevels,
                                                                                 @RequestParam(value = "location", required = false) String[] locations,
                                                                                 @RequestParam(value = "name", required = false) String name) throws NoSuchEntityException {

        ResourceFilterData filterData = new ResourceFilterData();
        filterData.setTechnology(arrToLong(technologies));
        filterData.setProject(projects);
        filterData.setEngineerLevel(arrToInteger(engineerLevels));
        filterData.setLocation(arrToLong(locations));
        filterData.setName(name);

        FullResourceColumnDtoList fullResourceColumnDtoList = resourceBordService.getColumns(filterData);

        return new ResponseEntity<>(fullResourceColumnDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/resource_columns/update_priority", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResourceColumnDtos> updatePriority(@RequestBody ResourceColumnPriority[] columnPriorities) throws NoSuchEntityException {

        List<ResourceColumnDto> columns = resourceBordService.updatePriority(columnPriorities);

        Collections.sort(columns, (o1, o2) -> o1.getPriority() - o2.getPriority());
        ResourceColumnDtos columnDtos = new ResourceColumnDtos();
        columnDtos.setColumns(columns);

        return new ResponseEntity<>(columnDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/resource_columns/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResourceColumnDtos> getColumns() throws NoSuchEntityException {

        List<ResourceColumnDto> columns = resourceBordService.getColumnsList(false);

        ResourceColumnDtos columnDtos = new ResourceColumnDtos();
        columnDtos.setColumns(columns);

        return new ResponseEntity<>(columnDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/resource_columns/sort", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<FullResourceColumnDtoList> sortColumns(@RequestBody SortingColumnsObject columnPriorities) throws NoSuchEntityException {

        FullResourceColumnDtoList columns = resourceBordService.sort(columnPriorities);

        return new ResponseEntity<>(columns, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/resource_columns/sorted_list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResourceColumnDtos> getSortedColumns() throws NoSuchEntityException {

        List<ResourceColumnDto> columns = resourceBordService.getColumnsList(true);

        ResourceColumnDtos columnDtos = new ResourceColumnDtos();
        columnDtos.setColumns(columns);

        return new ResponseEntity<>(columnDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/resource_columns/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<FullResourceColumnDtoList> deleteColumn(@PathVariable("id") Long id) throws NoSuchEntityException {

        resourceBordService.deleteColumn(id);

        return new ResponseEntity<>(new FullResourceColumnDtoList(), HttpStatus.OK);
    }

    private Integer[] arrToInteger(String[] arr) {
        if (arr == null) {
            return null;
        }
        Integer[] data = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = Integer.parseInt(arr[i]);
        }
        return data;
    }

    private Long[] arrToLong(String[] arr) {
        if (arr == null) {
            return null;
        }
        Long[] data = new Long[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = Long.parseLong(arr[i]);
        }
        return data;
    }
}
