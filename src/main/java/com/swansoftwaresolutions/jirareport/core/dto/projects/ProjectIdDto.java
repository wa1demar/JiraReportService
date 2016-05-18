package com.swansoftwaresolutions.jirareport.core.dto.projects;

/**
 * @author Vladimir Martynyuk
 */
public class ProjectIdDto {
    private Long projectId;
    private Long assignmentTypeId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getAssignmentTypeId() {
        return assignmentTypeId;
    }

    public void setAssignmentTypeId(Long assignmentTypeId) {
        this.assignmentTypeId = assignmentTypeId;
    }
}
