package com.swansoftwaresolutions.jirareport.core.dto.groups;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class JiraGroupsDto {
    private List<JiraGroupDto> groups;

    public List<JiraGroupDto> getGroups() {
        return groups;
    }

    public void setGroups(List<JiraGroupDto> groups) {
        this.groups = groups;
    }
}
