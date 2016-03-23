package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.UserDto;
import com.swansoftwaresolutions.jirareport.core.service.UserService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.web.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest/v1")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    private ResponseEntity<UserDto> getUserProfile() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.retrieveByUsername(auth.getName());

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    private ResponseEntity<UserDto> updateProfile(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) throws NoSuchEntityException {

        if (bindingResult.hasErrors()) {

            throw new InvalidRequestException("Invalid request", bindingResult);
        }

        UserDto updatedUserDto = userService.update(userDto);

        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }
}
