package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.UserDto;
import com.swansoftwaresolutions.jirareport.core.dto.UserLoginDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

/**
 * @author Vladimir Martynyuk
 */
public interface UserService {
    UserDto retrieveByUsername(String username);

    UserLoginDto retrieveLoggerUser(String username);

    UserDto update(UserDto userDto) throws NoSuchEntityException;
}
