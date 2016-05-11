package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface JiraProjectRepository {

    JiraProject add(JiraProject jiraProject);

    List<JiraProject> findAll();

    JiraProject findById(Long projectId);

    JiraProject findByKey(String key) throws NoSuchEntityException;

    void delete(final JiraProject jiraProject) throws NoSuchEntityException;

    void delete(final Long projectId) throws NoSuchEntityException;

    JiraProject update(JiraProject jiraProject) throws NoSuchEntityException;

    void save(List<JiraProject> projects);
}
