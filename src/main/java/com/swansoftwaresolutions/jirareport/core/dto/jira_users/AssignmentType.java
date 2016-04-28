package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

/**
 * @author Vladimir Martynyuk
 */
public class AssignmentType {
    private Long fromAssignmentTypeId;
    private Long toAssignmentTypeId;

    public Long getFromAssignmentTypeId() {
        return fromAssignmentTypeId;
    }

    public void setFromAssignmentTypeId(Long fromAssignmentTypeId) {
        this.fromAssignmentTypeId = fromAssignmentTypeId;
    }

    public Long getToAssignmentTypeId() {
        return toAssignmentTypeId;
    }

    public void setToAssignmentTypeId(Long toAssignmentTypeId) {
        this.toAssignmentTypeId = toAssignmentTypeId;
    }
}
