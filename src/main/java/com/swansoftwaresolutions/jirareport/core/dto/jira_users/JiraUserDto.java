package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

import com.swansoftwaresolutions.jirareport.core.dto.position.PositionDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;

/**
 * @author Vladimir Martynyuk
 */
public class JiraUserDto {

    private String login;
    private String name;
    private PositionDto position;
    private ResourceColumnDto assignmentType;

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

    public PositionDto getPosition() {
        return position;
    }

    public void setPosition(PositionDto position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JiraUserDto userDto = (JiraUserDto) o;

        if (!login.equals(userDto.login)) return false;
        return name.equals(userDto.name);

    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public ResourceColumnDto getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(ResourceColumnDto assignmentType) {
        this.assignmentType = assignmentType;
    }
}
