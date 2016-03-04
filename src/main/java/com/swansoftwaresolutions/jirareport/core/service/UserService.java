package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.rest.dto.UserDto;
import com.swansoftwaresolutions.jirareport.rest.dto.UserLoginDto;

/**
 * @author Vladimir Martynyuk
 */
public interface UserService {
    UserDto retrieveByUsername(String username);

    UserLoginDto retrieveLoggerUser(String username);
}
