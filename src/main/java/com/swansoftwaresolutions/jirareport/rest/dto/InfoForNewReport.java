package com.swansoftwaresolutions.jirareport.rest.dto;

import com.swansoftwaresolutions.jirareport.sheduller.dto.ProjectDto;

import java.util.List;

/**
 * @author Vitaliy Holovko
 *         on 04.03.16.
 */
public class InfoForNewReport {

    public List<ProjectDto> projects;
    public List<JiraUserDto> users;
}
