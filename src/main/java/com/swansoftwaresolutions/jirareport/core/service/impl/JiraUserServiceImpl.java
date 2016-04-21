package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.NewResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.LocationRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.ResourceBordRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.TechnologyRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author Vitaliy Holovko
 */

@Service
public class JiraUserServiceImpl implements JiraUserService {

    @Autowired
    JiraUserRepository jiraUserRepository;

    @Autowired
    TechnologyRepository technologyRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ResourceBordRepository resourceBordRepository;

    @Autowired
    JiraUserMapper jiraUserMapper;

    @Autowired
    ConfigService configService;
    @Override
    public JiraUser save(JiraUser jiraUser) {
        return jiraUserRepository.add(jiraUser);
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

    @Override
    public ResourceUserDto addUserToBoard(NewResourceUserDto newResourceUserDto) throws NoSuchEntityException {
        JiraUser jiraUser = jiraUserRepository.findByLogin(newResourceUserDto.getUserLogin());

        jiraUser.setDescription(newResourceUserDto.getDescription());
        jiraUser.setLevel(newResourceUserDto.getLevel());
        jiraUser.setTechnologies(technologyRepository.findAllByIds(newResourceUserDto.getTechnologies()));

        jiraUser.setColumn(resourceBordRepository.findDefaultColumn());

        jiraUser.setLocation(locationRepository.findById(newResourceUserDto.getLocation()));

        JiraUser newJiraUser = jiraUserRepository.update(jiraUser);

        return jiraUserMapper.fromJiraUserToResourceUserDto(newJiraUser);
    }

    @Override
    public ResourceUserDto removeUserFromBoard(String login) throws NoSuchEntityException {
        JiraUser user = jiraUserRepository.deleteUserFromColumn(login);

        return jiraUserMapper.fromJiraUserToResourceUserDto(user);
    }

    @Override
    public ResourceUserDto removeUserFromBoardFully(String login) throws NoSuchEntityException {
        JiraUser user = jiraUserRepository.findByLogin(login);
        user.setColumn(null);

        return jiraUserMapper.fromJiraUserToResourceUserDto(jiraUserRepository.merge(user));
    }

    @Override
    public ResourceUserDto addAttachment(String login, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("/attachments/" + file)));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
