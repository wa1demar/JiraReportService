package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.swansoftwaresolutions.jirareport.core.dto.locations.LocationDto;
import com.swansoftwaresolutions.jirareport.core.dto.position.PositionDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class FullResourceUserDto {
    private String login;
    private String name;
    private PositionDto position;
    private List<TechnologyDto> technologies = new ArrayList<>();
    private List<ProjectDto> projects = new ArrayList<>();
    private LocationDto location;
    private String description;
    private String avatar;
    private List<AttachmentDto> attachments = new ArrayList<>();
    private ResourceColumnDto column;
    private int resourceOrder;

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

    @JsonPropertyOrder({ "id" })
    public List<TechnologyDto> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyDto> technologies) {
        this.technologies = technologies;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<AttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentDto> attachments) {
        this.attachments = attachments;
    }

    public ResourceColumnDto getColumn() {
        return column;
    }

    public void setColumn(ResourceColumnDto column) {
        this.column = column;
    }

    public int getResourceOrder() {
        return resourceOrder;
    }

    public void setResourceOrder(int resourceOrder) {
        this.resourceOrder = resourceOrder;
    }

    @JsonPropertyOrder({ "id" })
    public List<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }

    public void setPosition(PositionDto position) {
        this.position = position;
    }

    public PositionDto getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FullResourceUserDto that = (FullResourceUserDto) o;

        if (!login.equals(that.login)) return false;
        if (!name.equals(that.name)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return avatar != null ? avatar.equals(that.avatar) : that.avatar == null;

    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        return result;
    }
}
