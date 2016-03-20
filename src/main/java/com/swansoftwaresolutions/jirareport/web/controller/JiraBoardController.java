package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.JiraBoardsDto;
import com.swansoftwaresolutions.jirareport.core.service.JiraBoardService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
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
@RequestMapping("/rest")
public class JiraBoardController {
    @Autowired
    JiraBoardService jiraBoardService;

    @RequestMapping(value = "/v1/boards", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JiraBoardsDto> getAllBoards() {
        JiraBoardsDto jiraBoardDto = new JiraBoardsDto();
        try {
            jiraBoardDto = jiraBoardService.retrieveAllBoards();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(jiraBoardDto, HttpStatus.OK);
    }

}
