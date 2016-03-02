package com.swansoftwaresolutions.jirareport.core.services;

import com.swansoftwaresolutions.jirareport.core.entity.Project;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public interface ProjectService {

    Project save(Project project);

    List<Project> getAllProjects();

    void delete(Project project);
}
