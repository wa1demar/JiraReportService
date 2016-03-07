package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.mapper.UserMapper;
import com.swansoftwaresolutions.jirareport.core.repository.UserRepository;
import com.swansoftwaresolutions.jirareport.core.service.UserService;
import com.swansoftwaresolutions.jirareport.rest.dto.UserDto;
import com.swansoftwaresolutions.jirareport.rest.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDto retrieveByUsername(String username) {
        return userMapper.toDto(userRepository.findByUsername(username));
    }

    @Override
    public UserLoginDto retrieveLoggerUser(String username) {

        UserLoginDto userLoginDto = userMapper.loginToDto(userRepository.findByUsername(username));
        userLoginDto.setToken("xfdsfdgdfgfhfghfg ");
        return userLoginDto;
    }


}
