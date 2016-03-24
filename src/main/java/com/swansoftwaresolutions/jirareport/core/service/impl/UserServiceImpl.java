package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.user.*;
import com.swansoftwaresolutions.jirareport.core.mapper.UserMapper;
import com.swansoftwaresolutions.jirareport.core.service.ApplicationMailer;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.entity.User;
import com.swansoftwaresolutions.jirareport.domain.entity.builder.UserBuilder;
import com.swansoftwaresolutions.jirareport.domain.enums.UserStatus;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.UserRepository;
import com.swansoftwaresolutions.jirareport.core.service.UserService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JiraUserRepository jiraUserRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationMailer applicationMailer;

    @Override
    public UserDto retrieveByUsername(String username) {
        return userMapper.toDto(userRepository.findByUsername(username));
    }

    @Override
    public UserLoginDto retrieveLoggerUser(String username) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserLoginDto userLoginDto = userMapper.loginToDto(user);
        userLoginDto.setToken("xfdsfdgdfgfhfghfg ");
        return userLoginDto;
    }

    @Override
    public UserDto updateProfile(UserDto userDto) throws NoSuchEntityException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());

        User updatedUser = userRepository.update(user);

        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDto update(UserDto userDto) throws NoSuchEntityException {
        User existedUser = userRepository.findById(userDto.getId());
        userDto.setPassword(existedUser.getPassword());
        User updatedUser = userRepository.update(userMapper.fromDto(userDto));

        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDto changePassword(PasswordDto passwordDto) throws NoSuchEntityException {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(encoder.encode(passwordDto.getNewPassword()));

        User updatedUser = userRepository.update(user);

        return userMapper.toDto(updatedUser);
    }

    @Override
    public UsersDto retrieveAllUsers() {
        return userMapper.toDtos(userRepository.findAll());
    }

    @Override
    public UserDto invite(InviteUserDto inviteUserDto) throws NoSuchEntityException, MessagingException {
        User user = null;
        String password = RandomStringUtils.random(5, true, true);
        if (inviteUserDto.getInviteParam().indexOf("@") == -1) {
            JiraUser jiraUser = jiraUserRepository.findByLogin(inviteUserDto.getInviteParam());
            user =  userRepository.add(new UserBuilder()
                    .username(jiraUser.getLogin())
                    .fullName(jiraUser.getFullName())
                    .email(jiraUser.getEmail())
                    .password(encoder.encode(password))
                    .status(UserStatus.ACTIVE)
                    .build());
        } else {
            user =  userRepository.add(new UserBuilder()
                    .username(inviteUserDto.getInviteParam())
                    .email(inviteUserDto.getInviteParam())
                    .password(encoder.encode(password))
                    .status(UserStatus.ACTIVE)
                    .build());
        }

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Map model = new HashMap();
        user.setPassword(password);
        model.put("newUser", user);
        model.put("currentUserName", currentUser.getFullName().equals("") ? "" : currentUser.getFullName());
        applicationMailer.sendMail(user.getEmail(), "Swan Jira Service", model, "invite");

        return userMapper.toDto(user);
    }

    @Override
    public UserDto delete(Long userId) throws NoSuchEntityException {
        return userMapper.toDto(userRepository.delete(userId));
    }

    @Override
    public UserDto resetPassword(Long userId) throws NoSuchEntityException, MessagingException {
        String password = RandomStringUtils.random(5, true, true);

        User user = userRepository.findById(userId);
        user.setPassword(encoder.encode(password));

        User updatedUser = userRepository.update(user);

        if (updatedUser != null) {
            updatedUser.setPassword(password);
            Map model = new HashMap();
            user.setPassword(password);
            model.put("user", updatedUser);
            applicationMailer.sendMail(user.getEmail(), "Swan Jira Service", model, "reset");
        }

        return userMapper.toDto(updatedUser);
    }

}
