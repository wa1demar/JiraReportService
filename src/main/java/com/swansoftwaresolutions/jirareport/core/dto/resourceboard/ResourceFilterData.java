package com.swansoftwaresolutions.jirareport.core.dto.resourceboard;

/**
 * @author Vladimir Martynyuk
 */
public class ResourceFilterData {
    private Long[] technologies;
    private String[] projects;
    private Integer[] engineerLevels;
    private Long[] locations;
    private String name;

    public Integer[] getEngineerLevels() {
        return engineerLevels;
    }

    public void setEngineerLevels(Integer[] engineerLevels) {
        this.engineerLevels = engineerLevels;
    }

    public Long[] getLocations() {
        return locations;
    }

    public void setLocations(Long[] locations) {
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getProjects() {
        return projects;
    }

    public void setProjects(String[] projects) {
        this.projects = projects;
    }

    public Long[] getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Long[] technologies) {
        this.technologies = technologies;
    }
}
