package com.swansoftwaresolutions.jirareport.core.dto.jira_sprint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class JiraSprintsDto {

    private List<JiraSprintDto> sprints = new ArrayList<>();

    public List<JiraSprintDto> getSprints() {
        return sprints;
    }

    public void setSprints(List<JiraSprintDto> sprints) {
        this.sprints = sprints;
    }
}
