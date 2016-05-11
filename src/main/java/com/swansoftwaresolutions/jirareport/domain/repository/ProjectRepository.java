package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Project;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ProjectRepository {
    Project save(Project project);

    List<Project> findAll();
}
