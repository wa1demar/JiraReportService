package com.swansoftwaresolutions.jirareport.core.dto.sprint_issue;

import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */
public class SprintIssueListDto implements Serializable {

    private List<SprintIssueDto> sprintIssueDtos = new ArrayList<>();

    public List<SprintIssueDto> getSprintIssueDtos() {
        return sprintIssueDtos;
    }

    public void setSprintIssueDtos(List<SprintIssueDto> sprintIssueDtos) {
        this.sprintIssueDtos = sprintIssueDtos;
    }
}
