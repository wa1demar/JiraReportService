package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.LogWork;
import com.swansoftwaresolutions.jirareport.core.dto.TimeSheetDto;
import com.swansoftwaresolutions.jirareport.core.service.TimeSheetService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraIssue;
import com.swansoftwaresolutions.jirareport.domain.entity.User;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraIssueRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class TimeSheetServiceImpl implements TimeSheetService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JiraIssueRepository jiraIssueRepository;

    @Override
    public TimeSheetDto findMy() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(auth.getName());

        List<JiraIssue> issues = jiraIssueRepository.findByUserLogin(user.getUsername());

        TimeSheetDto timeSheetDto = new TimeSheetDto();
        timeSheetDto.setUserName(user.getFullName());

        List<LogWork> logWorks = new ArrayList<>();
        for (JiraIssue issue : issues) {
            LogWork logWork = new LogWork();
            logWork.setIssueKey(issue.getKey());
            logWork.setDate(issue.getUpdated());
            logWork.setDescription(issue.getDescription());

            logWorks.add(logWork);
        }

        timeSheetDto.setIssues(logWorks);


        return timeSheetDto;
    }
}
