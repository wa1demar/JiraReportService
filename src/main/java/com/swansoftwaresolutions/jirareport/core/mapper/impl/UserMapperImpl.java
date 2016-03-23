package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.user.UserDto;
import com.swansoftwaresolutions.jirareport.core.dto.user.UserLoginDto;
import com.swansoftwaresolutions.jirareport.core.dto.user.UsersDto;
import com.swansoftwaresolutions.jirareport.core.mapper.UserMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

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

    @Override
    public UsersDto toDtos(List<User> users) {
        Type targetistType = new TypeToken<List<UserDto>>(){}.getType();
        List<UserDto> usersDto = modelMapper.map(users, targetistType);
        UsersDto result = new UsersDto();
        result.setUsers(usersDto);
        return result;
    }


}
