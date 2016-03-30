package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Vitaliy Hollovko
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest")
public class ConfigController {

    @Autowired
    ConfigService configService;

    @RequestMapping(value = "/v1/config", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ConfigDto> getConfig() {
        return new ResponseEntity<>(configService.retrieveConfig(), HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/config", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ConfigDto> patchConfig(@Valid @RequestBody ConfigDto configDto) throws NoSuchEntityException {

        return new ResponseEntity<>(configService.update(configDto), HttpStatus.OK);
    }
}
