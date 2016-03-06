package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraUserDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraUserService {

    JiraUser save(JiraUser jiraUser);

    List<JiraUserDto> findAll();

    void delete(JiraUser jiraUser) throws NoSuchEntityException;
}
