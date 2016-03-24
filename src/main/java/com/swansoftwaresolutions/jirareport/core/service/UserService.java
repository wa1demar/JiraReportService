package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.user.*;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import javax.mail.MessagingException;

/**
 * @author Vladimir Martynyuk
 */
public interface UserService {
    UserDto retrieveByUsername(String username);

    UserLoginDto retrieveLoggerUser(String username);

    UserDto updateProfile(UserDto userDto) throws NoSuchEntityException;

    UserDto update(UserDto userDto) throws NoSuchEntityException;

    UserDto changePassword(PasswordDto passwordDto) throws NoSuchEntityException;

    UsersDto retrieveAllUsers();

    UserDto invite(InviteUserDto inviteUserDto) throws NoSuchEntityException, MessagingException;

    UserDto delete(Long userDto) throws NoSuchEntityException;

    UserDto resetPassword(Long userId) throws NoSuchEntityException, MessagingException;
}
