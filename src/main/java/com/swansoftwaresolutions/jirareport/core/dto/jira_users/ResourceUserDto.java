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
    private List<TechnologyDto> technologies = new ArrayList<>();
    private LocationDto location;
    private ResourceColumnDto column;
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

    public ResourceColumnDto getColumn() {
        return column;
    }

    public void setColumn(ResourceColumnDto column) {
        this.column = column;
    }
}
