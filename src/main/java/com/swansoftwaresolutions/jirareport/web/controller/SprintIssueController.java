package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.IssuesByDayDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.SprintIssueListDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.SprintIssuesDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.service.SprintIssueService;
import com.swansoftwaresolutions.jirareport.core.service.SprintService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
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

        List<IssuesByDayDto> results = new ArrayList<>();

        long oneDayMilSec = 86400000; // number of milliseconds in one day
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        SprintDto sprint = new SprintDto();

        SprintIssuesDto result = new SprintIssuesDto();

        SprintIssueListDto sprintIssueListDto = sprintIssueService.findBySprintIdAndAsignee(sprintId, assignee);
        try {
            sprint = sprintService.findById(sprintId);

            Date startDate = sprint.getStartDate();
            Date endDate = sprint.getEndDate();

            long startDateMilSec = startDate.getTime();
            long endDateMilSec = endDate.getTime();

            for(long d=startDateMilSec; d<=endDateMilSec; d=d+oneDayMilSec){
                List<SprintIssueDto> issues = new ArrayList<>();
                Date date1 = new Date();
                Date date2 = new Date();
                for (SprintIssueDto sprintIssueDto: sprintIssueListDto.getSprintIssueDtos()){
                    try {
                        date1 = new Date(d);
                        date2 = sdf.parse(sprintIssueDto.getIssueDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (isSameDate(date1,date2)){
                        issues.add(sprintIssueDto);
                    }
                }

                IssuesByDayDto issuesByDayDto = new IssuesByDayDto();
                issuesByDayDto.setDate(sdf.format(d));
                issuesByDayDto.setIssues(issues);
                results.add(issuesByDayDto);
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

    public  boolean isSameDate(Date date, Date anotherDate) {
        if(date==null && anotherDate==null){
            return true;
        }
        else if(date==null || anotherDate==null){
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar anotherCalendar = Calendar.getInstance();
        anotherCalendar.setTime(anotherDate);
        anotherCalendar.set(Calendar.HOUR_OF_DAY, 0);
        anotherCalendar.set(Calendar.MINUTE, 0);
        anotherCalendar.set(Calendar.SECOND, 0);
        anotherCalendar.set(Calendar.MILLISECOND, 0);
        anotherCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        return calendar.compareTo(anotherCalendar) == 0;
    }

}
