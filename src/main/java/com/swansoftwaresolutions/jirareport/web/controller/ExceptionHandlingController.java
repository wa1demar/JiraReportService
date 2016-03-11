package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vladimir Martynyuk
 */
@RestController
public class ExceptionHandlingController {

    @ExceptionHandler({NoSuchEntityException.class})
    public HttpStatus noSuchEntityError() {
        return HttpStatus.OK;
    }
}
