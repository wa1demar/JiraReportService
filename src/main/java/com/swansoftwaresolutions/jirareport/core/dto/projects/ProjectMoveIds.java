package com.swansoftwaresolutions.jirareport.core.dto.projects;

/**
 * @author Vladimir Martynyuk
 */
public class ProjectMoveIds {
    private Long fromProjectId;
    private Long toProjectId;

    public Long getFromProjectId() {
        return fromProjectId;
    }

    public void setFromProjectId(Long fromProjectId) {
        this.fromProjectId = fromProjectId;
    }

    public Long getToProjectId() {
        return toProjectId;
    }

    public void setToProjectId(Long toProjectId) {
        this.toProjectId = toProjectId;
    }
}
