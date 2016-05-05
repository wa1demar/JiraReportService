package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class MoveMemberDto {
    private AssignmentType assignmentType;
    private List<MemberPositionDto> users = new ArrayList<>();

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
}
