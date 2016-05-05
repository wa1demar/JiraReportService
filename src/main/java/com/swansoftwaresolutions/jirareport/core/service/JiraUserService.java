package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MemberDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.MoveMemberDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.NewResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ResourceUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyId;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraUserService {

    JiraUser save(JiraUser jiraUser);

    JiraUsersDto retrieveAllUsers();

    void delete(JiraUser jiraUser) throws NoSuchEntityException;

    List<JiraUserDto> findAll();

    JiraUsersDto retrieveFilteredUsers();

    ResourceUserDto addUserToBoard(NewResourceUserDto resourceUserDto) throws NoSuchEntityException;

    ResourceUserDto removeUserFromBoard(String login) throws NoSuchEntityException;

    ResourceUserDto removeUserFromBoardFully(String login) throws NoSuchEntityException;

    ResourceUserDto addAttachment(String login, MultipartFile file) throws NoSuchEntityException;

    ResourceUserDto findInfoByLogin(String login) throws NoSuchEntityException;

    ResourceUserDto updateMemberInfo(String login, MemberDto memberDto) throws NoSuchEntityException;

    ResourceUserDto addTechnologies(String login, TechnologyId technologyIds) throws NoSuchEntityException;

    ResourceUserDto deleteTechnology2(String login, Long technologyId) throws NoSuchEntityException;

    void deleteAttachment(Long id);

    ResourceUserDto moveMember(String login, MoveMemberDto memberDto) throws NoSuchEntityException;
}