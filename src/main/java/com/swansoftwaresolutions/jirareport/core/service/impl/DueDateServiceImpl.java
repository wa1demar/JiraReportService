package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_issue.DueDateDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_issue.DueDatesDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraIssueMapper;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.core.service.DueDateService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.model.Paged;
import com.swansoftwaresolutions.jirareport.domain.repository.DueDateRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class DueDateServiceImpl implements DueDateService {

    @Autowired
    DueDateRepository dueDateRepository;

    @Autowired
    JiraProjectRepository jiraProjectRepository;

    @Autowired
    JiraIssueMapper jiraIssueMapper;


    @Autowired
    ConfigService configService;

    @Override
    public DueDatesDto retrieveDueDates(int page) {

        String agileDoneName = configService.retrieveConfig().getDueDateIssueStatus();
        List<String> agileDoneNames = new ArrayList<>();
        for (String dateString : Arrays.asList(agileDoneName.split(","))) {
            agileDoneNames.add(dateString);
        }

        Map<String, String> projects = jiraProjectRepository.findAll().stream().collect(Collectors.toMap(JiraProject::getKey, p -> p.getName()));

        Paged paged = dueDateRepository.retrieveAllDueDatas(agileDoneNames, page);

        List<JiraIssue> datas = paged.getList();


        List<DueDateDto> dates = new ArrayList<>();
        for (JiraIssue issue : datas) {
            List<Date> dds = issue.getDueDates().stream().map(r -> r.getDueDate()).collect(Collectors.toList());
            Date [] datesArray = new Date[dds.size()];
            Date [] datesArray2 = {issue.getDueDate()};

            Collections.sort(dds, (o1, o2) -> o2.compareTo(o1));

            DueDateDto dateDto = new DueDateDto();
            dateDto.setId(issue.getId());
            dateDto.setAssignee(issue.getAssignedFullName());
            dateDto.setKey(issue.getKey());
            dateDto.setDueDate(dds.toArray(datesArray).length > 0 ? dds.toArray(datesArray) : datesArray2);
            dateDto.setDescription(issue.getDescription());
            dateDto.setProject(projects.get(issue.getProjectKey()));
            dateDto.setStatus(issue.getStatusName());
            dateDto.setSummary(issue.getSummary());

            dates.add(dateDto);
        }

        DueDatesDto result = new DueDatesDto();
        result.setDates(dates);
        result.setTotalPages((int)Math.floor(paged.getTotal() / 10) + 1);
        result.setPage(page);
        result.setTotalItems(paged.getTotal());

        return result;
    }
}
