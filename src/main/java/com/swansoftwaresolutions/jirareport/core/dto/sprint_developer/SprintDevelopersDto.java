package com.swansoftwaresolutions.jirareport.core.dto.sprint_developer;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class SprintDevelopersDto implements Serializable {

    private List<SprintDeveloperDto> developers;

    public List<SprintDeveloperDto> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<SprintDeveloperDto> developers) {
        this.developers = developers;
    }
}
