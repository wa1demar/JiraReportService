package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.AssignmentType;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MemberDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.NewResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyId;
import com.swansoftwaresolutions.jirareport.core.mapper.TechnologyMapper;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.domain.entity.Attachment;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import com.swansoftwaresolutions.jirareport.domain.entity.Technology;
import com.swansoftwaresolutions.jirareport.domain.repository.*;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    AttachmentRepository attachmentRepository;

    @Autowired
    JiraUserMapper jiraUserMapper;

    @Autowired
    TechnologyMapper technologyMapper;

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

        ResourceColumn defaultColumn = resourceBordRepository.findById(newResourceUserDto.getAssignmentTypeId());
        jiraUser.setColumns(new ArrayList<ResourceColumn>() {{
            add(defaultColumn);
        }});

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
        user.setColumns(null);

        return jiraUserMapper.fromJiraUserToResourceUserDto(jiraUserRepository.merge(user));
    }

    @Override
    public ResourceUserDto addAttachment(String login, MultipartFile file) throws NoSuchEntityException {
        if (!file.isEmpty()) {
            try {
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "uploads" + File.separator + login);
                if (!dir.exists())
                    dir.mkdirs();

                File attachmentFile = new File(dir.getAbsolutePath() + File.separator + new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(attachmentFile));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();

                addAttachmentToUser(FilenameUtils.removeExtension(file.getOriginalFilename()), attachmentFile, login);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return findInfoByLogin(login);
    }

    private void addAttachmentToUser(String name, File file, String login) throws NoSuchEntityException {
        JiraUser user = jiraUserRepository.findByLogin(login);

        Attachment attachment = new Attachment();
        attachment.setJiraUser(user);
        attachment.setTitle(name);
        attachment.setExtension(FilenameUtils.getExtension(file.getAbsolutePath()));
        attachment.setUrl(file.getAbsolutePath());

        attachmentRepository.save(attachment);
    }

    @Override
    public ResourceUserDto findInfoByLogin(String login) throws NoSuchEntityException {
        JiraUser user = jiraUserRepository.findByLogin(login);
        return jiraUserMapper.fromJiraUserToResourceUserDto(user);
    }

    @Override
    public ResourceUserDto updateMemberInfo(String login, MemberDto memberDto) throws NoSuchEntityException {
        JiraUser user = jiraUserRepository.updateJiraUserInfo(login, memberDto);

        AssignmentType assignmentType = memberDto.getAssignmentType();
        if (memberDto.getAssignmentType() != null) {
            Set<ResourceColumn> columns = new HashSet<>(user.getColumns());
            columns.removeIf(resourceColumn -> resourceColumn.getId().equals(assignmentType.getFromAssignmentTypeId()));

            columns.add(resourceBordRepository.findById(assignmentType.getToAssignmentTypeId()));
            user.setColumns(new ArrayList<>(columns));

            user = jiraUserRepository.update(user);
        }
        return jiraUserMapper.fromJiraUserToResourceUserDto(user);
    }

    @Override
    public ResourceUserDto addTechnologies(String login, TechnologyId technologyId) throws NoSuchEntityException {
        JiraUser user = jiraUserRepository.findByLogin(login);

        Set<Technology> technologies = new HashSet<>(user.getTechnologies());

        technologies.add(technologyRepository.findById(technologyId.getTechnologyId()));

        user.setTechnologies(new ArrayList<>(technologies));

        return jiraUserMapper.fromJiraUserToResourceUserDto(jiraUserRepository.update(user));
    }

    @Override
    public ResourceUserDto deleteTechnology2(String login, Long technologyId) throws NoSuchEntityException {
        Technology technology = technologyRepository.findById(technologyId);

        List<JiraUser> users = technology.getUsers();

        technology.setUsers(users.parallelStream().filter(i -> !i.getLogin().equals(login)).collect(Collectors.toList()));
        technologyRepository.update(technology);

        return jiraUserMapper.fromJiraUserToResourceUserDto(jiraUserRepository.findByLogin(login));
    }

    @Override
    public void deleteAttachment(Long id) {
        Attachment attachment = attachmentRepository.findById(id);

        File file = new File(attachment.getUrl());
        file.delete();

        attachmentRepository.delete(attachment);

    }
}
