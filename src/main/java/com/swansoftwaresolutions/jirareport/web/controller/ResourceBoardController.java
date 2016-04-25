package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDtoList;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDtos;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnPriority;
import com.swansoftwaresolutions.jirareport.core.service.ResourceBordService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<FullResourceColumnDtoList> getColumnsWithUsers() throws NoSuchEntityException {

        FullResourceColumnDtoList fullResourceColumnDtoList = resourceBordService.getColumns();

        return new ResponseEntity<>(fullResourceColumnDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/resource_columns/update_priority", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResourceColumnDtos> updatePriority(@RequestBody ResourceColumnPriority[] columnPriorities) throws NoSuchEntityException {

        List<ResourceColumnDto> columns = resourceBordService.updatePriority(columnPriorities);

        ResourceColumnDtos columnDtos = new ResourceColumnDtos();
        columnDtos.setColumns(columns);

        return new ResponseEntity<>(columnDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/resource_columns/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResourceColumnDtos> getColumns() throws NoSuchEntityException {

        List<ResourceColumnDto> columns = resourceBordService.getColumnsList();

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
}
