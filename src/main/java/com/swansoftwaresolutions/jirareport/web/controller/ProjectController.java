package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.projects.FullProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.FullProjectDtos;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.projects.ProjectDtos;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/v1/projects/{id}", method = RequestMethod.PUT)
    private ResponseEntity<ProjectDto> updateProject(@PathVariable("id") Long id, @Valid @RequestBody ProjectDto projectDto) throws NoSuchEntityException {
        projectDto.setId(id);
        ProjectDto newProjectDto = projectService.update(projectDto);

        return new ResponseEntity<>(newProjectDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/projects", method = RequestMethod.GET)
    private ResponseEntity<ProjectDtos> getProjects() throws NoSuchEntityException {
        ProjectDtos allProjects = projectService.findAll();

        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/projects/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<ProjectDtos> getProjects(@PathVariable("id") Long id) throws NoSuchEntityException {
        projectService.delete(id);

        ProjectDtos allProjects = projectService.findAll();

        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/projects/list_with_users", method = RequestMethod.GET)
    private ResponseEntity<FullProjectDtos> getProjectsWithUsers() throws NoSuchEntityException {
        FullProjectDtos allProjects = projectService.findAllFull();

        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/projects/{id}/members/{login:.+}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<FullProjectDto> deleteMember(@PathVariable("login") String login, @PathVariable("id") Long id) throws NoSuchEntityException {

        FullProjectDto newNewResourceUserDto = projectService.deleteMember(login, id);

        return new ResponseEntity<>(newNewResourceUserDto, HttpStatus.OK);
    }
}
