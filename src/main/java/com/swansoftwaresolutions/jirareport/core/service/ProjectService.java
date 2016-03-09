package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface ProjectService {

    Project save(Project project);

    Project findByKey(String key) throws NoSuchEntityException;

    List<Project> findAll();

    void delete(Project project) throws NoSuchEntityException;
}
