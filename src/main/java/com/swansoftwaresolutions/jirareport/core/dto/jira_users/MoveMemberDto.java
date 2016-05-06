package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceFilterData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class MoveMemberDto {
    private AssignmentType assignmentType;
    private List<MemberPositionDto> users = new ArrayList<>();
    private ResourceFilterData filters;

    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    public List<MemberPositionDto> getUsers() {
        return users;
    }

    public void setUsers(List<MemberPositionDto> users) {
        this.users = users;
    }

    public ResourceFilterData getFilters() {
        return filters;
    }

    public void setFilters(ResourceFilterData filters) {
        this.filters = filters;
    }
}
