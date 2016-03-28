package com.swansoftwaresolutions.jirareport.core.dto.sprint;

import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Vladimir Martynyuk
 */
public class FullSprintDtoBuilder {

    private FullSprintDto sprintDto;

    public FullSprintDtoBuilder() {
        this.sprintDto = new FullSprintDto();
    }

    public FullSprintDtoBuilder id(long id) {
        this.sprintDto.setId(id);
        return this;
    }

    public FullSprintDtoBuilder name(String name) {
        this.sprintDto.setName(name);
        return this;
    }

    public FullSprintDtoBuilder notCountTarget(boolean notCountTarget) {
        this.sprintDto.setNotCountTarget(notCountTarget);
        return this;
    }

    public FullSprintDtoBuilder showUat(boolean showUat) {
        this.sprintDto.setShowUat(showUat);
        return this;
    }

    public FullSprintDtoBuilder showOutOfRange(boolean showOutOfRange) {
        this.sprintDto.setShowOutOfRange(showOutOfRange);
        return this;
    }

    public FullSprintDtoBuilder state(String state) {
        this.sprintDto.setState(state);
        return this;
    }

    public FullSprintDtoBuilder type(int type) {
        this.sprintDto.setType(type);
        return this;
    }

    public FullSprintDtoBuilder endDate(Date endDate) {
        this.sprintDto.setEndDate(endDate);
        return this;
    }

    public FullSprintDtoBuilder startDate(Date startDate) {
        this.sprintDto.setStartDate(startDate);
        return this;
    }

    public FullSprintDtoBuilder reportId(Long reportId) {
        this.sprintDto.setReportId(reportId);
        return this;
    }

    public FullSprintDtoBuilder developers(List<SprintDeveloperDto> developers) {
        this.sprintDto.setDevelopers(developers);
        return this;
    }

    public FullSprintDto build() {
        return sprintDto;
    }
}
