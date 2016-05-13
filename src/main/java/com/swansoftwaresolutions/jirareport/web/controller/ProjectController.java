package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDtos;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/v1/projects", method = RequestMethod.POST)
    private ResponseEntity<ProjectDto> addProject(@Valid @RequestBody ProjectDto projectDto) throws NoSuchEntityException {
        ProjectDto newProjectDto = projectService.add(projectDto);

        return new ResponseEntity<>(newProjectDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/projects", method = RequestMethod.GET)
    private ResponseEntity<ProjectDtos> getProjects() throws NoSuchEntityException {
        ProjectDtos allProjects = projectService.findAll();

        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/projects_with_users", method = RequestMethod.GET)
    private ResponseEntity<ProjectDtos> getProjectsWithUsers() throws NoSuchEntityException {
        ProjectDtos allProjects = projectService.findAll();

        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }
}
