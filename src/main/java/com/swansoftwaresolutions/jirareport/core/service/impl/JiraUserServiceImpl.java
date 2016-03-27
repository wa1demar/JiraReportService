package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.ConfigDto;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserAutoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Vitaliy Holovko
 */

@Service
public class JiraUserServiceImpl implements JiraUserService {

    @Autowired
    JiraUserRepository jiraUserRepository;

    @Autowired
    JiraUserMapper jiraUserMapper;

    @Autowired
    ConfigService configService;

    @Override
    public JiraUser save(JiraUser jiraUser) {
        return jiraUserRepository.add(jiraUser);
    }

    @Override
    public List<JiraUserDto> saveAll(List<JiraUserAutoDto> jiraUserAutoDtoList) throws NoSuchEntityException {
        jiraUserAutoDtoList.removeAll(jiraUserMapper.toAutoDtos(jiraUserRepository.findAll()));

        List<JiraUserDto> newJiraUser = new ArrayList<>();
        for (JiraUserAutoDto jiraUserAuto : jiraUserAutoDtoList) {
            newJiraUser.add(jiraUserMapper.toDto(jiraUserRepository.add(jiraUserMapper.fromAutoDto(jiraUserAuto))));
        }
        return newJiraUser;
    }

    @Override
    public JiraUsersDto retrieveAllUsers() {
        List<JiraUserDto> userDtoList = jiraUserMapper.toDtos(jiraUserRepository.findAll());
        JiraUsersDto usersDto = new JiraUsersDto();
        usersDto.setUsers(userDtoList);
        return usersDto;
    }

    @Override
    public List<JiraUserDto> findAll() {
        return jiraUserMapper.toDtos(jiraUserRepository.findAll());
    }

    @Override
    public void delete(JiraUser jiraUser) throws NoSuchEntityException {
        jiraUserRepository.delete(jiraUser);
    }

    @Override
    public JiraUser findByLogin(String login) throws NoSuchEntityException{
        return jiraUserRepository.findByLogin(login);
    }

    @Override
    public JiraUsersDto retrieveFilteredUsers() {

        ConfigDto configDto = configService.retrieveConfig();

        if (configDto.getJiraDevGroupName().equals("")) {
            return retrieveAllUsers();
        }

        String[] groups = configDto.getJiraDevGroupName().split(",");
        Set<JiraUser> byGroups = new HashSet<>(jiraUserRepository.findByGroups(groups));
        List<JiraUserDto> userDtoList = jiraUserMapper.toDtos(new ArrayList<>(byGroups));

        JiraUsersDto usersDto = new JiraUsersDto();
        usersDto.setUsers(userDtoList);
        return usersDto;
    }
}
