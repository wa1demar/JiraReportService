package com.swansoftwaresolutions.jirareport.core.dto.resourceboard;

/**
 * @author Vladimir Martynyuk
 */
public class ResourceFilterData {
    private Long[] technology;
    private Long[] project;
    private Long[] engineerLevel;
    private Long[] location;
    private String name;

    public Long[] getEngineerLevel() {
        return engineerLevel;
    }

    public void setEngineerLevel(Long[] engineerLevel) {
        this.engineerLevel = engineerLevel;
    }

    public Long[] getLocation() {
        return location;
    }

    public void setLocation(Long[] location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long[] getProject() {
        return project;
    }

    public void setProject(Long[] project) {
        this.project = project;
    }

    public Long[] getTechnology() {
        return technology;
    }

    public void setTechnology(Long[] technology) {
        this.technology = technology;
    }
}
