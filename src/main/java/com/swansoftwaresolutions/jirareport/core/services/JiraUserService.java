package com.swansoftwaresolutions.jirareport.core.services;

import com.swansoftwaresolutions.jirareport.core.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.entity.User;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface JiraUserService {

    JiraUser save(JiraUser jiraUser);

    List<JiraUser> findAll();

    void delete(JiraUser jiraUser) throws NoSuchEntityException;
}
