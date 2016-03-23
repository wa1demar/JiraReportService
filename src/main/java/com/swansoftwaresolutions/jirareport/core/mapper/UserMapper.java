package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.user.UserDto;
import com.swansoftwaresolutions.jirareport.core.dto.user.UserLoginDto;
import com.swansoftwaresolutions.jirareport.domain.entity.User;

/**
 * @author Vladimir Martynyuk
 */
public interface UserMapper {

    UserDto toDto(User user);

    UserLoginDto loginToDto(User user);
}
