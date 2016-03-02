package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.Project;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ProjectRepository {
    Project add(Project project);

    List<Project> getAllProjects();

    void deleteProject(final Project project);

    void deleteProject(final Long projectId);
}
