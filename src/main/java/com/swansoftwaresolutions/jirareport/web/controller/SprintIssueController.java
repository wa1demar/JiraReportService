package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.IssuesByDayDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.SprintIssueListDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.SprintIssuesDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.service.SprintIssueService;
import com.swansoftwaresolutions.jirareport.core.service.SprintService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.web.controller.helper.HelperMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Vitaliy Holovko
 */
@RestController
@RequestMapping("/rest")
public class SprintIssueController {

    @Autowired
    SprintIssueService sprintIssueService;

    @Autowired
    SprintService sprintService;

    @RequestMapping(value = "/v1/sprint_issues/{sprintId}/{assignee}", method = RequestMethod.GET)
    private ResponseEntity<List<IssuesByDayDto>> getAllIssues(@PathVariable("sprintId") Long sprintId, @PathVariable("assignee") String assignee) {

        HelperMethods helperMethods = new HelperMethods();

        List<IssuesByDayDto> results = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        SprintIssuesDto result = new SprintIssuesDto();

        SprintIssueListDto sprintIssueListDto = sprintIssueService.findBySprintIdAndAsignee(sprintId, assignee);
        try {
            SprintDto sprint = sprintService.findById(sprintId);

            Calendar startDate = Calendar.getInstance();
            startDate.setTime(sprint.getStartDate());

            Calendar endDate = Calendar.getInstance();
            endDate.setTime(sprint.getEndDate());

            while(!startDate.after(endDate)){
                Date currentDate = startDate.getTime();

                List<SprintIssueDto> issues = new ArrayList<>();

                if (helperMethods.isWeekend(currentDate)){
                    startDate.add(Calendar.DATE, 1);
                    continue;
                }

                for (SprintIssueDto sprintIssueDto: sprintIssueListDto.getSprintIssueDtos()){
                    Date date2 = null;
                    try {
                        date2 = sdf.parse(sprintIssueDto.getIssueDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (helperMethods.isSameDate(currentDate,date2)){
                        issues.add(sprintIssueDto);
                    }
                }

                IssuesByDayDto issuesByDayDto = new IssuesByDayDto();
                issuesByDayDto.setDate(sdf.format(currentDate));
                issuesByDayDto.setIssues(issues);
                results.add(issuesByDayDto);

                startDate.add(Calendar.DATE, 1);
            }

        } catch (NoSuchEntityException e) {
           e.printStackTrace();
        }

        return new ResponseEntity<>(results, HttpStatus.OK);
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

    @RequestMapping(value = "/v1/sprint_issues/{issueId}", method = RequestMethod.PUT)
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

    @RequestMapping(value = "/v1/sprint_issues/{issueId}", method = RequestMethod.DELETE)
    private ResponseEntity<SprintIssueDto> delete(@PathVariable("issueId") Long issueId) throws NoSuchEntityException {
        sprintIssueService.delete(issueId);
        return new ResponseEntity<>(new SprintIssueDto(), HttpStatus.OK);
    }

}
