package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ProjectRepository {

    Project add(Project project);

    List<Project> findAll();

    Project findById(Long projectId);

    Project findByKey(String key);

    void delete(final Project project) throws NoSuchEntityException;

    void delete(final Long projectId) throws NoSuchEntityException;

    Project update(Project project) throws NoSuchEntityException;
}
