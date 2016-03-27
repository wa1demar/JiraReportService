package com.swansoftwaresolutions.jirareport.rest.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.JiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUserDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraGroup;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraGroupRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.rest.service.UserImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class UserImporterServiceImpl implements UserImporterService {

    @Autowired
    @Qualifier("restClient")
    RestClient restClient;

    @Autowired
    JiraGroupRepository jiraGroupRepository;

    @Autowired
    JiraUserRepository jiraUserRepository;

    @Autowired
    JiraUserMapper jiraUserMapper;

    @Override
    public void importUsersFromJira() {
        List<JiraGroup> groups = jiraGroupRepository.findAll();

        for (JiraGroup group : groups) {
            ImportedJiraUsersDto users = restClient.loadAllUsersByGroupName(group.getName());

            List<ImportedJiraUserDto> usersList = users.getValues();

            jiraUserRepository.saveAll(jiraUserMapper.fromDtos(usersList), group);
        }
    }
}
