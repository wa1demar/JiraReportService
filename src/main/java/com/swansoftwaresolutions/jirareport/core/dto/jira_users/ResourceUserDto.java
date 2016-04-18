package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swansoftwaresolutions.jirareport.core.dto.locations.LocationDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class ResourceUserDto {

    private String login;
    private String name;
    private Integer engineerLevel;
    private List<TechnologyDto> experiences = new ArrayList<>();
    private LocationDto location;
    @JsonIgnore
    private List<ResourceColumnDto> columns;
    private String description;

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

    public List<TechnologyDto> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<TechnologyDto> experiences) {
        this.experiences = experiences;
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

    public List<ResourceColumnDto> getColumns() {
        return columns;
    }

    public void setColumns(List<ResourceColumnDto> columns) {
        this.columns = columns;
    }
}
