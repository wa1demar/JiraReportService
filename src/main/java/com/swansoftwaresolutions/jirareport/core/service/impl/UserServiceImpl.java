package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.user.PasswordDto;
import com.swansoftwaresolutions.jirareport.core.mapper.UserMapper;
import com.swansoftwaresolutions.jirareport.core.service.exception.WrongPasswordException;
import com.swansoftwaresolutions.jirareport.domain.entity.User;
import com.swansoftwaresolutions.jirareport.domain.repository.UserRepository;
import com.swansoftwaresolutions.jirareport.core.service.UserService;
import com.swansoftwaresolutions.jirareport.core.dto.user.UserDto;
import com.swansoftwaresolutions.jirareport.core.dto.user.UserLoginDto;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder encoder;

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

    @Override
    public UserDto update(UserDto userDto) throws NoSuchEntityException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());

        User updatedUser = userRepository.update(user);

        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDto changePassword(PasswordDto passwordDto) throws NoSuchEntityException {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(encoder.encode(passwordDto.getNewPassword()));

        User updatedUser = userRepository.update(user);

        return userMapper.toDto(updatedUser);
    }


}
