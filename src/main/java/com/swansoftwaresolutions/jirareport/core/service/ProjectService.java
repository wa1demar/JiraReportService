package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface ProjectService {

    JiraProject save(JiraProject jiraProject);

    JiraProject findByKey(String key) throws NoSuchEntityException;

    List<JiraProject> findAll();

    void delete(JiraProject jiraProject) throws NoSuchEntityException;
}
