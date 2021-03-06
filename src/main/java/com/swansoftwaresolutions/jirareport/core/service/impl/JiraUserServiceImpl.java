package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.*;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectIdDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.FullResourceColumnDtoList;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyId;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.TechnologyMapper;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.core.service.ResourceBordService;
import com.swansoftwaresolutions.jirareport.domain.entity.*;
import com.swansoftwaresolutions.jirareport.domain.repository.*;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.apache.commons.io.FilenameUtils;
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
    JiraUsersReferencesRepository jiraUsersReferencesRepository;

    @Autowired
    TechnologyRepository technologyRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ResourceBordRepository resourceBordRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ResourceBordService resourceBordService;

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
//        jiraUser.setPosition(newResourceUserDto.getLevel());
        jiraUser.setTechnologies(technologyRepository.findAllByIds(newResourceUserDto.getTechnologies()));

        ResourceColumn defaultColumn = resourceBordRepository.findById(newResourceUserDto.getAssignmentTypeId());
        JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
        jiraUsersReferences.setColumn(defaultColumn);
        jiraUsersReferences.setUser(jiraUser);
        JiraUsersReferences newJiraUsersReferences = jiraUsersReferencesRepository.add(jiraUsersReferences);

        jiraUser.setUserReferences(new ArrayList<JiraUsersReferences>() {{
            add(newJiraUsersReferences);
        }});

        jiraUser.setLocation(locationRepository.findById(newResourceUserDto.getLocation()));
        jiraUser.setPosition(positionRepository.findById(newResourceUserDto.getLevel()));

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
        JiraUser user = jiraUserRepository.deleteUserFromColumn(login);

        for (Attachment attachment : user.getAttachments()) {
            deleteAttachment(attachment.getId());
        }
        for (Technology technology : user.getTechnologies()) {
            deleteTechnology2(user.getLogin(), technology.getId());
        }

        user.setDescription("");
        user.setLocation(null);
        user.setNotShowCircles(false);
        user.setPosition(null);
        user.setResourceOrder(0);
        user.setTechnologies(new ArrayList<>());

        JiraUser updatedUser = jiraUserRepository.update(user);

        return jiraUserMapper.fromJiraUserToResourceUserDto(updatedUser);
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
        if (assignmentType != null) {
            List<Project> projects = jiraUsersReferencesRepository.findByUserAndAssignmentType(user.getLogin(), assignmentType.getFromAssignmentTypeId());
            jiraUsersReferencesRepository.deleteByAssignmentType(user.getLogin(), assignmentType.getFromAssignmentTypeId());

            ResourceColumn resourceColumn = resourceBordRepository.findById(assignmentType.getToAssignmentTypeId());

            if (projects != null && projects.size() > 0) {
                for (Project project : projects) {
                    JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
                    jiraUsersReferences.setUser(user);
                    jiraUsersReferences.setColumn(resourceColumn);
                    jiraUsersReferences.setProject(project);

                    jiraUsersReferencesRepository.add(jiraUsersReferences);
                }
            } else {
                JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
                jiraUsersReferences.setUser(user);
                jiraUsersReferences.setColumn(resourceColumn);

                jiraUsersReferencesRepository.add(jiraUsersReferences);
            }

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
        JiraUser user = jiraUserRepository.findByLogin(login);
        Set<Technology> technologies = new HashSet<>(user.getTechnologies());
        technologies.removeIf(t -> t.getId().equals(technologyId));
        user.setTechnologies(new ArrayList<>(technologies));
        jiraUserRepository.deleteTechnology(login, technologyId);

        return jiraUserMapper.fromJiraUserToResourceUserDto(user);
    }

    @Override
    public void deleteAttachment(Long id) {
        Attachment attachment = attachmentRepository.findById(id);

        File file = new File(attachment.getUrl());
        file.delete();

        attachmentRepository.delete(attachment);

    }

    @Override
    public ResourceUserDto moveMember(String login, MoveMemberDto memberDto) throws NoSuchEntityException {
        JiraUser jiraUser = jiraUserRepository.findByLogin(login);
        AssignmentType assignmentType = memberDto.getAssignmentType();

        if (assignmentType != null) {
            if (memberDto.getProjectId() == null) {
                List<Project> projects = jiraUsersReferencesRepository.findByUserAndAssignmentType(jiraUser.getLogin(), assignmentType.getFromAssignmentTypeId());
                jiraUsersReferencesRepository.deleteByAssignmentType(jiraUser.getLogin(), assignmentType.getFromAssignmentTypeId());

                ResourceColumn resourceColumn = resourceBordRepository.findById(assignmentType.getToAssignmentTypeId());

                if (projects != null && projects.size() > 0) {
                    for (Project project : projects) {
                        JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
                        jiraUsersReferences.setUser(jiraUser);
                        jiraUsersReferences.setColumn(resourceColumn);
                        jiraUsersReferences.setProject(project);

                        jiraUsersReferencesRepository.add(jiraUsersReferences);
                    }
                } else {
                    JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
                    jiraUsersReferences.setUser(jiraUser);
                    jiraUsersReferences.setColumn(resourceColumn);

                    jiraUsersReferencesRepository.add(jiraUsersReferences);
                }
            } else {
                Project project = projectRepository.findById(memberDto.getProjectId());
                jiraUsersReferencesRepository.deleteByAssignmentTypeAndProject(jiraUser.getLogin(), assignmentType.getFromAssignmentTypeId(), memberDto.getProjectId());

                ResourceColumn resourceColumn = resourceBordRepository.findById(assignmentType.getToAssignmentTypeId());

                JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
                jiraUsersReferences.setUser(jiraUser);
                jiraUsersReferences.setColumn(resourceColumn);
                jiraUsersReferences.setProject(project);

                jiraUsersReferencesRepository.add(jiraUsersReferences);


            }

        }

        jiraUserRepository.sortMembers(memberDto.getUsers());
        return jiraUserMapper.fromJiraUserToResourceUserDto(jiraUser);
    }

    @Override
    public FullResourceColumnDtoList moveMemberFull(String login, MoveMemberDto memberDto) throws NoSuchEntityException {
        JiraUser jiraUser = jiraUserRepository.findByLogin(login);
        AssignmentType assignmentType = memberDto.getAssignmentType();

//        if (memberDto.getAssignmentType() != null) {
//            Set<ResourceColumn> columns = new HashSet<>(jiraUser.getColumns());
//            columns.removeIf(resourceColumn -> resourceColumn.getId().equals(assignmentType.getFromAssignmentTypeId()));
//
//            columns.add(resourceBordRepository.findById(assignmentType.getToAssignmentTypeId()));
//            jiraUser.setColumns(new ArrayList<>(columns));
//
//            jiraUser = jiraUserRepository.update(jiraUser);
//        }

        jiraUserRepository.sortMembers(memberDto.getUsers());

        return resourceBordService.getColumns(memberDto.getFilters());
    }

    @Override
    public ResourceUserDto addProject(String login, ProjectIdDto projectId) throws NoSuchEntityException {
        Project project = projectRepository.findById(projectId.getProjectId());
        JiraUser jiraUser = jiraUserRepository.findByLogin(login);
        ResourceColumn resourceColumn = resourceBordRepository.findById(projectId.getAssignmentTypeId());

        List<JiraUsersReferences> userReferences = jiraUser.getUserReferences();

        if (userReferences != null && userReferences.size() > 0) {
            List<Project> projects = new ArrayList<>();
            for (JiraUsersReferences references : userReferences) {
                if (references.getProject() != null) {
                    projects.add(references.getProject());
                }
            }
            if (projects.size() == 0) {
                jiraUsersReferencesRepository.deleteByAssignmentType(jiraUser.getLogin());
            }
        }

        JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
        jiraUsersReferences.setColumn(resourceColumn);
        jiraUsersReferences.setUser(jiraUser);
        jiraUsersReferences.setProject(project);

        jiraUsersReferencesRepository.add(jiraUsersReferences);
        jiraUser.getUserReferences().add(jiraUsersReferences);

        return jiraUserMapper.fromJiraUserToResourceUserDto(jiraUser);
    }

    @Override
    public ResourceUserDto deleteProject(String login, Long projectId) throws NoSuchEntityException {
        Project project = projectRepository.findById(projectId);
        JiraUser jiraUser = jiraUserRepository.findByLogin(login);

        jiraUsersReferencesRepository.deleteByProject(jiraUser.getLogin(), project.getId());

        jiraUser = jiraUserRepository.findByLogin(login);
        if (jiraUser.getUserReferences().size() == 0) {
            ResourceColumn bench = resourceBordRepository.findDefaultColumn();
            JiraUsersReferences jiraUsersReferences = new JiraUsersReferences();
            jiraUsersReferences.setColumn(bench);
            jiraUsersReferences.setUser(jiraUser);
            jiraUsersReferencesRepository.add(jiraUsersReferences);

            jiraUser.setUserReferences(new ArrayList<JiraUsersReferences>() {{
                add(jiraUsersReferences);
            }});

        }

        return jiraUserMapper.fromJiraUserToResourceUserDto(jiraUser);
    }
}
