package com.swansoftwaresolutions.jirareport.core.dto.jira_users;

import com.swansoftwaresolutions.jirareport.core.dto.locations.LocationDto;
import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Attachment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class FullResourceUserDto {
    private String login;
    private String name;
    private Integer engineerLevel;
    private List<TechnologyDto> technologies = new ArrayList<>();
    private LocationDto location;
    private String description;
    private String avatar;
    private List<AttachmentDto> attachments = new ArrayList<>();
    private ResourceColumnDto column;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FullResourceUserDto userDto = (FullResourceUserDto) o;

        if (!login.equals(userDto.login)) return false;
        if (name != null ? !name.equals(userDto.name) : userDto.name != null) return false;
        if (engineerLevel != null ? !engineerLevel.equals(userDto.engineerLevel) : userDto.engineerLevel != null)
            return false;
        if (description != null ? !description.equals(userDto.description) : userDto.description != null) return false;
        return avatar != null ? avatar.equals(userDto.avatar) : userDto.avatar == null;

    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (engineerLevel != null ? engineerLevel.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        return result;
    }
}
