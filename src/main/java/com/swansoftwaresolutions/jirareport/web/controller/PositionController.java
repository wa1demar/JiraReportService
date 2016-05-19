package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.position.PositionDto;
import com.swansoftwaresolutions.jirareport.core.dto.position.PositionDtos;
import com.swansoftwaresolutions.jirareport.core.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Vladimir Martynyuk
 */
@RestController
public class PositionController {

    @Autowired
    PositionService positionService;

    @RequestMapping(value = "/rest/v1/positions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<PositionDtos> getAllPositions() {

        PositionDtos positionDtos = positionService.findAll();

        return new ResponseEntity<>(positionDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/positions", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PositionDto> addPosition(@Valid @RequestBody PositionDto positionDto) {

        PositionDto newPositionDto = positionService.add(positionDto);

        return new ResponseEntity<>(newPositionDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/positions/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<PositionDto> deletePosition(@PathVariable("id") Long id) {

        PositionDto deletedPositionDto = positionService.delete(id);

        return new ResponseEntity<>(deletedPositionDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/v1/positions/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PositionDto> updatePosition(@PathVariable("id") Long id, @Valid @RequestBody PositionDto positionDto) {
        positionDto.setId(id);
        PositionDto updatedPositionDto = positionService.update(positionDto);

        return new ResponseEntity<>(updatedPositionDto, HttpStatus.OK);
    }

}
