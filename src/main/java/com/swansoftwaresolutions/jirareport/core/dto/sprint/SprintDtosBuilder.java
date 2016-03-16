package com.swansoftwaresolutions.jirareport.core.dto.sprint;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class SprintDtosBuilder {
    private SprintDtos sprintDtos;

    public SprintDtosBuilder() {
        this.sprintDtos = new SprintDtos();
    }

    public SprintDtosBuilder sprints(List<SprintDto> sprints) {
        this.sprintDtos.setSprints(sprints);
        return this;
    }

    public SprintDtos build() {
        return sprintDtos;
    }
}
