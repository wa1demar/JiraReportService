package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.user.PasswordDto;
import com.swansoftwaresolutions.jirareport.core.dto.user.UserDto;
import com.swansoftwaresolutions.jirareport.core.dto.user.UserLoginDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

/**
 * @author Vladimir Martynyuk
 */
public interface UserService {
    UserDto retrieveByUsername(String username);

    UserLoginDto retrieveLoggerUser(String username);

    UserDto update(UserDto userDto) throws NoSuchEntityException;

    UserDto changePassword(PasswordDto passwordDto) throws NoSuchEntityException;
}
