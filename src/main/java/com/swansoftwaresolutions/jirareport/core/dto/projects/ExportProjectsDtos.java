package com.swansoftwaresolutions.jirareport.core.dto.projects;

import com.swansoftwaresolutions.jirareport.core.dto.jira_users.JiraUserDto;

import java.util.*;

/**
 * @author Vladimir Martynyuk
 */
public class ExportProjectsDtos {
    private List<FullProjectDto> projects = new ArrayList<>();
    private Map<String, Set<JiraUserDto>> technologies = new HashMap<>();

    public List<FullProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<FullProjectDto> projects) {
        this.projects = projects;
    }

    public Map<String, Set<JiraUserDto>> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Map<String, Set<JiraUserDto>> technologies) {
        this.technologies = technologies;
    }
}
