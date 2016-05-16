package com.swansoftwaresolutions.jirareport.core.dto.projects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class FullProjectDtos {
    private List<FullProjectDto> projects = new ArrayList<>();

    public List<FullProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<FullProjectDto> projects) {
        this.projects = projects;
    }
}
