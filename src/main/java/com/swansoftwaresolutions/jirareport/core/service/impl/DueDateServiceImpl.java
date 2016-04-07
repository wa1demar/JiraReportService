package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_issue.DueDateDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_issue.DueDatesDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraIssueMapper;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.core.service.DueDateService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;
import com.swansoftwaresolutions.jirareport.domain.model.Paged;
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


    @Autowired
    ConfigService configService;

    @Override
    public DueDatesDto retrieveDueDates(int page) {

        String agileDoneName = configService.retrieveConfig().getAgileDoneName();
        List<String> agileDoneNames = new ArrayList<>();
        for (String dateString : Arrays.asList(agileDoneName.split(","))) {
            agileDoneNames.add(dateString);
        }

        Paged paged = dueDateRepository.retrieveAllDueDatas(agileDoneNames, page);

        List<JiraIssue> datas = paged.getList();


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
            dateDto.setProject(issue.getProjectKey());
            dateDto.setStatus(issue.getStatusName());
            dateDto.setSummary(issue.getDescription());

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
