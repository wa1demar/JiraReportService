package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.sprint_issue.IssuesByDayDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_issue.SprintIssueListDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintIssueMapper;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.core.service.SprintIssueService;
import com.swansoftwaresolutions.jirareport.core.service.SprintService;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintIssueRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.helper.HelperMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Vitliy Holovko
 */
@Service
public class SprintIssueServiceImpl implements SprintIssueService {

    @Autowired
    SprintIssueMapper sprintIssueMapper;

    @Autowired
    SprintIssueRepository sprintIssueRepository;

    @Autowired
    SprintService sprintService;

    @Autowired
    ConfigService configService;

    @Override
    public List<SprintIssueDto> findAll() throws NoSuchEntityException {
        return sprintIssueMapper.toDtos(sprintIssueRepository.findAll());
    }

    @Override
    public SprintIssueListDto findBySprintIdAndAsignee(Long sprintId, String assignee) {
        List<SprintIssueDto> list = sprintIssueMapper.toDtos(sprintIssueRepository.findBySprintIdAndAssignee(sprintId, assignee));
        SprintIssueListDto sprintIssueListDto = new SprintIssueListDto();
        sprintIssueListDto.setSprintIssueDtos(list);

        return sprintIssueListDto;
    }

    @Override
    public SprintIssueListDto findBySprintId(Long sprintId) {
        List<SprintIssueDto> list = sprintIssueMapper.toDtos(sprintIssueRepository.findBySprintId(sprintId));
        SprintIssueListDto sprintIssueListDto = new SprintIssueListDto();
        sprintIssueListDto.setSprintIssueDtos(list);

        return sprintIssueListDto;
    }

    @Override
    public SprintIssueDto add(SprintIssueDto sprintIssueDto) {
        return sprintIssueMapper.toDto(sprintIssueRepository.add(sprintIssueMapper.fromDto(sprintIssueDto)));
    }

    @Override
    public SprintIssueDto update(SprintIssueDto sprintIssueDto) {
        try {
            return sprintIssueMapper.toDto(sprintIssueRepository.update(sprintIssueMapper.fromDto(sprintIssueDto)));
        } catch (NoSuchEntityException e) {
            return new SprintIssueDto();
        }
    }

    @Override
    public void delete(Long issueId) throws NoSuchEntityException {
        sprintIssueRepository.delete(issueId);
    }

    @Override
    public List<IssuesByDayDto> getIssuesByDay(Long sprintId, String assignee) {
        List<IssuesByDayDto> results = new ArrayList<>();

        HelperMethods helperMethods = new HelperMethods();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        SprintIssueListDto sprintIssueListDto = findBySprintIdAndAsignee(sprintId, assignee);
        try {
            SprintDto sprint = sprintService.findById(sprintId);

            Calendar startDate = Calendar.getInstance();
            startDate.setTime(sprint.getStartDate());

            Calendar endDate = Calendar.getInstance();
            endDate.setTime(sprint.getEndDate());

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String nonWorkingDaysString = configService.retrieveConfig().getNonWorkingDays();
            List<Date>  nonWorkingDays = new ArrayList<>();
            for (String dateString : Arrays.asList(nonWorkingDaysString.split(","))) {
                try {
                    if (dateString.isEmpty()) continue;
                    nonWorkingDays.add(formatter.parse(dateString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


            while(!startDate.after(endDate)){
                Date currentDate = startDate.getTime();

                List<SprintIssueDto> issues = new ArrayList<>();

                if (helperMethods.isWeekend(currentDate) || nonWorkingDays.contains(currentDate)){
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
        return results;
    }
}

