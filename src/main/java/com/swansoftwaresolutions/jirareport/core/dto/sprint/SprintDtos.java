package com.swansoftwaresolutions.jirareport.core.dto.sprint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class SprintDtos implements Serializable {

    private List<SprintDto> sprints = new ArrayList<>();

    public List<SprintDto> getSprints() {
        return sprints;
    }

    public void setSprints(List<SprintDto> sprints) {
        this.sprints = sprints;
    }
}
