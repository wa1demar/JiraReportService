package com.swansoftwaresolutions.jirareport.core.dto.sprint_developer;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class SprintDevelopersDtoBuilder {

    private SprintDevelopersDto developersDto;

    public SprintDevelopersDtoBuilder() {
        this.developersDto = new SprintDevelopersDto();
    }

    public SprintDevelopersDtoBuilder developers(List<SprintDeveloperDto> developers) {
        this.developersDto.setDevelopers(developers);
        return this;
    }

    public SprintDevelopersDto build() {
        return developersDto;
    }
}
