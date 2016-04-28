package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

/**
 * @author Vladimir Martynyuk
 */
public class MemberDto {

    private int engineerLevel;
    private String description;
    private Long locationId;
    private AssignmentType assignmentType;

    public int getEngineerLevel() {
        return engineerLevel;
    }

    public void setEngineerLevel(int engineerLevel) {
        this.engineerLevel = engineerLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }
}
