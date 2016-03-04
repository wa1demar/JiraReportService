package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.entity.User;
import com.swansoftwaresolutions.jirareport.core.mapper.UserMapper;
import com.swansoftwaresolutions.jirareport.rest.dto.UserDto;
import com.swansoftwaresolutions.jirareport.rest.dto.UserLoginDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserLoginDto loginToDto(User user) {
        user.setPassword("");
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUser(toDto(user));
        return userLoginDto;
    }


}
