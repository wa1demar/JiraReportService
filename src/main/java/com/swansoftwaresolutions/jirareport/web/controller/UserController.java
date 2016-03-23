package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.UserDto;
import com.swansoftwaresolutions.jirareport.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        userDto.setPassword(null);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
