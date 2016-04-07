package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_issue.DueDateDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_issue.DueDatesDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraIssueMapper;
import com.swansoftwaresolutions.jirareport.core.service.DueDateService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;
import com.swansoftwaresolutions.jirareport.domain.repository.DueDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class DueDateServiceImpl implements DueDateService {

    @Autowired
    DueDateRepository dueDateRepository;

    @Autowired
    JiraIssueMapper jiraIssueMapper;
    @Override
    public DueDatesDto retrieveDueDates() {

        List<JiraIssue> datas = dueDateRepository.retrieveAllDueDatas();

       List<DueDateDto> dates = new ArrayList<>();
        for (JiraIssue issue : datas) {
            List<Date> dds = issue.getDueDates().stream().map(r -> r.getDueDate()).collect(Collectors.toList());
            Date [] datesArray = new Date[dds.size()];

            List<String> descs = issue.getDueDates().stream().map(r -> r.getDescription()).collect(Collectors.toList());
            String [] descsArray = new String[descs.size()];

            DueDateDto dateDto = new DueDateDto();
            dateDto.setId(issue.getId());
            dateDto.setAssignee(issue.getAssignedFullName());
            dateDto.setKey(issue.getKey());
            dateDto.setDueDate(dds.toArray(datesArray));
            dateDto.setDescription(descs.toArray(descsArray));
            dateDto.setProjict(issue.getProjectKey());
            dateDto.setStatus(issue.getStatusName());
            dateDto.setSummary(issue.getDescription());

            dates.add(dateDto);
        }

        DueDatesDto result = new DueDatesDto();
        result.setDates(dates);

        return result;
    }
}
