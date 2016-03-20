package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.SprintIssueListDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.core.service.SprintIssueService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vitaliy Holovko
 */
@RestController
@RequestMapping("/rest")
public class SprintIssueController {

    @Autowired
    SprintIssueService sprintIssueService;

    @RequestMapping(value = "/v1/sprint_issues/{sprintId}/{assignee}", method = RequestMethod.GET)
    private ResponseEntity<SprintIssueListDto> getAllIssues(@PathVariable("sprintId") Long sprintId, @PathVariable("assignee") String assignee) {
        SprintIssueListDto sprintIssueListDto = sprintIssueService.findBySprintIdAndAsignee(sprintId, assignee);
        return new ResponseEntity<>(sprintIssueListDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/sprint_issues/{sprintId}/{assignee}", method = RequestMethod.POST)
    private ResponseEntity<SprintIssueDto> addIssue(@PathVariable("sprintId") Long sprintId, @PathVariable("assignee") String assignee, @RequestBody SprintIssueDto newSprintIssue) throws NoSuchEntityException {
        newSprintIssue.setSprintId(sprintId);
        newSprintIssue.setAssignee(assignee);
        SprintIssueDto sprintIssueDto = sprintIssueService.add(newSprintIssue);

        return new ResponseEntity<>(sprintIssueDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/sprint_issues/", method = RequestMethod.POST)
    private ResponseEntity<SprintIssueDto> addIssueNew(@RequestBody SprintIssueDto newSprintIssue) throws NoSuchEntityException {
        SprintIssueDto sprintIssueDto = sprintIssueService.add(newSprintIssue);

        return new ResponseEntity<>(sprintIssueDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/sprint_issues/{issueId}/", method = RequestMethod.PUT)
    private ResponseEntity<SprintIssueDto> updateIssue(@PathVariable("issueId") Long issueId, @RequestBody SprintIssueDto newSprintIssue) throws NoSuchEntityException {
        newSprintIssue.setId(issueId);
        SprintIssueDto sprintIssueDto = sprintIssueService.update(newSprintIssue);

        return new ResponseEntity<>(sprintIssueDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/sprint_issues/", method = RequestMethod.PUT)
    private ResponseEntity<SprintIssueDto> updateIssueNew(@RequestBody SprintIssueDto newSprintIssue) throws NoSuchEntityException {
        SprintIssueDto sprintIssueDto = sprintIssueService.update(newSprintIssue);
        return new ResponseEntity<>(sprintIssueDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/sprint_issues/{issueId}/", method = RequestMethod.DELETE)
    private ResponseEntity<SprintIssueDto> delete(@PathVariable("issueId") Long issueId) throws NoSuchEntityException {
        sprintIssueService.delete(issueId);
        return new ResponseEntity<>(new SprintIssueDto(), HttpStatus.OK);
    }

}
