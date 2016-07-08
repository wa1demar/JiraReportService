package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.TimeSheetDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologiesDto;
import com.swansoftwaresolutions.jirareport.core.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vladimir Martynyuk
 */
@RestController
public class TimeSheetController {

    @Autowired
    TimeSheetService timeSheetService;

    @RequestMapping(value = "/rest/v1/timescheet", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<TimeSheetDto> getAllTechnologies() {

        TimeSheetDto timeSheetDto = timeSheetService.findMy();

        return new ResponseEntity<>(timeSheetDto, HttpStatus.OK);
    }
}
