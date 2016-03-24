package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.user.InviteUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.user.PasswordDto;
import com.swansoftwaresolutions.jirareport.core.dto.user.UserDto;
import com.swansoftwaresolutions.jirareport.core.dto.user.UsersDto;
import com.swansoftwaresolutions.jirareport.core.service.UserService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.web.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
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

        UserDto updatedUserDto = userService.updateProfile(userDto);

        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/profile/change_password", method = RequestMethod.PUT)
    private ResponseEntity<UserDto> updatePassword(@Valid @RequestBody PasswordDto passwordDto, BindingResult bindingResult) throws NoSuchEntityException {

        if (bindingResult.hasErrors()) {

            throw new InvalidRequestException("Invalid request", bindingResult);
        }

        UserDto updatedUserDto = userService.changePassword(passwordDto);

        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/system_users", method = RequestMethod.GET)
    private ResponseEntity<UsersDto> allUsers() {

        UsersDto users = userService.retrieveAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/system_users/invite", method = RequestMethod.POST)
    private ResponseEntity<UserDto> inviteUser(@Valid @RequestBody InviteUserDto inviteUserDto) throws NoSuchEntityException, MessagingException {

        UserDto userDto = userService.invite(inviteUserDto);

        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    @RequestMapping(value = "/system_users/{user_id}", method = RequestMethod.PUT)
    private ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("user_id") Long userId) throws NoSuchEntityException {
        userDto.setId(userId);
        UserDto updatedUserDto = userService.update(userDto);

        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/system_users/{user_id}", method = RequestMethod.DELETE)
    private ResponseEntity<UserDto> deleteUser(@PathVariable("user_id") Long userId) throws NoSuchEntityException {
        UserDto userDto = userService.delete(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
