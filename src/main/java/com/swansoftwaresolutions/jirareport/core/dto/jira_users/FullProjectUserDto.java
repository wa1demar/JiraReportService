package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class FullProjectUserDto {
    private String login;
    private String name;
    private Integer engineerLevel;
    private ProjectDto column;
    private List<ResourceColumnDto> assignmentTypes = new ArrayList<>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEngineerLevel() {
        return engineerLevel;
    }

    public void setEngineerLevel(Integer engineerLevel) {
        this.engineerLevel = engineerLevel;
    }

    @JsonPropertyOrder({ "priority" })
    public List<ResourceColumnDto> getAssignmentTypes() {
        return assignmentTypes;
    }

    public void setAssignmentTypes(List<ResourceColumnDto> assignmentTypes) {
        this.assignmentTypes = assignmentTypes;
    }

    public ProjectDto getColumn() {
        return column;
    }

    public void setColumn(ProjectDto column) {
        this.column = column;
    }
}
