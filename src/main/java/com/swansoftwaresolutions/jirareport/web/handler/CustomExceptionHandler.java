package com.swansoftwaresolutions.jirareport.web.handler;

import com.swansoftwaresolutions.jirareport.core.service.exception.WrongPasswordException;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.web.exception.InvalidRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidRequestException.class})
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
        InvalidRequestException ire = (InvalidRequestException) e;
        List<FieldErrorResource> fieldErrorResources = new ArrayList<FieldErrorResource>();

        List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            FieldErrorResource fieldErrorResource = new FieldErrorResource();
            fieldErrorResource.setResource(fieldError.getObjectName());
            fieldErrorResource.setField(fieldError.getField());
            fieldErrorResource.setCode(fieldError.getCode());
            fieldErrorResource.setMessage(fieldError.getDefaultMessage());
            fieldErrorResources.add(fieldErrorResource);
        }

        ErrorResource error = new ErrorResource("InvalidRequest", ire.getMessage());
        error.setFieldErrors(fieldErrorResources);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler({NoSuchEntityException.class})
    protected ResponseEntity<Object> handleNoSuchEntity(RuntimeException e, WebRequest request) {
        InvalidRequestException ire = (InvalidRequestException) e;
        ErrorResource error = new ErrorResource("InvalidRequest", ire.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({WrongPasswordException.class})
    protected ResponseEntity<Object> handleWrongPassword(RuntimeException e, WebRequest request) {
        WrongPasswordException ire = (WrongPasswordException) e;
        ErrorResource error = new ErrorResource("InvalidRequest", ire.getMessage());

        List<FieldErrorResource> fieldErrorResources = new ArrayList<>();

        FieldErrorResource fieldErrorResource = new FieldErrorResource();
        fieldErrorResource.setResource("passwordDto");
        fieldErrorResource.setField("oldPassword");
        fieldErrorResource.setCode("PasswordsEqualConstraint");
        fieldErrorResource.setMessage(ire.getMessage());
        fieldErrorResources.add(fieldErrorResource);

        error.setFieldErrors(fieldErrorResources);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.NOT_FOUND, request);
    }


}
