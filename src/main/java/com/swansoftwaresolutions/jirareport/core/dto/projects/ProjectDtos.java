package com.swansoftwaresolutions.jirareport.core.dto.projects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ProjectDtos {
    private List<ProjectDto> projects = new ArrayList<>();

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }
}
