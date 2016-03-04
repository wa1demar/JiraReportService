package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.entity.User;
import com.swansoftwaresolutions.jirareport.rest.dto.UserDto;
import com.swansoftwaresolutions.jirareport.rest.dto.UserLoginDto;

/**
 * @author Vladimir Martynyuk
 */
public interface UserMapper {

    UserDto toDto(User user);

    UserLoginDto loginToDto(User user);
}
