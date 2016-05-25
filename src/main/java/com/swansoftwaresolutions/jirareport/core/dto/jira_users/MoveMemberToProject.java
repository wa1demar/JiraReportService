package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectFilterData;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectMoveIds;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class MoveMemberToProject {
    private ProjectMoveIds projects;
    private Long assignmentTypeId;
    private List<MemberPositionDto> users = new ArrayList<>();
    private ProjectFilterData filters;

    public List<MemberPositionDto> getUsers() {
        return users;
    }

    public void setUsers(List<MemberPositionDto> users) {
        this.users = users;
    }

    public ProjectMoveIds getProjects() {
        return projects;
    }

    public void setProjects(ProjectMoveIds projects) {
        this.projects = projects;
    }

    public ProjectFilterData getFilters() {
        return filters;
    }

    public void setFilters(ProjectFilterData filters) {
        this.filters = filters;
    }

    public Long getAssignmentTypeId() {
        return assignmentTypeId;
    }

    public void setAssignmentTypeId(Long assignmentTypeId) {
        this.assignmentTypeId = assignmentTypeId;
    }
}
