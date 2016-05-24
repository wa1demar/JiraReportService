package com.swansoftwaresolutions.jirareport.core.dto.projects;

/**
 * @author Vladimir Martynyuk
 */
public class ProjectFilterData {
    private Long[] technology;
    private Long[] project;
    private Long[] assignmentType;
    private Long[] engineerLevel;
    private Long[] location;

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

    public Long[] getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(Long[] assignmentType) {
        this.assignmentType = assignmentType;
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
