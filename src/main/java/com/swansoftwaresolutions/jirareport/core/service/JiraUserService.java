package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraUserAutoDto;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraUsersDto;

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
}