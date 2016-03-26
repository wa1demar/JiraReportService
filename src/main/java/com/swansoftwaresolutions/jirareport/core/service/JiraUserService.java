package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserAutoDto;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraUserService {

    JiraUser save(JiraUser jiraUser);

    List<JiraUserDto> saveAll(List<JiraUserAutoDto> jiraUserAutoDtoList) throws NoSuchEntityException;

    JiraUsersDto retrieveAllUsers();

    void delete(JiraUser jiraUser) throws NoSuchEntityException;

    List<JiraUserDto> findAll();

    JiraUser findByLogin(String login) throws NoSuchEntityException;

    JiraUsersDto retrieveFilteredUsers();
}