package com.swansoftwaresolutions.jirareport.sheduller.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserAutoDto;
import com.swansoftwaresolutions.jirareport.rest.client.AbstractRestClient;
import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraUserSchedulerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Vitaliy Holovko
 */

@Component("userClient")
public class UserClient extends AbstractRestClient implements RestClient {

    static Logger log = Logger.getLogger(UserClient.class.getName());

    @Autowired
    JiraUserService jiraUserService;

    String USER_URL = "https://swansoftwaresolutions.atlassian.net/rest/api/2/user/search?username=%";

    private JiraUserAutoDto fromDto(JiraUserSchedulerDto userDto) {
        JiraUserAutoDto jiraUser = new JiraUserAutoDto();
        jiraUser.setEmail(userDto.getEmailAddress());
        jiraUser.setFullName(userDto.getDisplayName());
        jiraUser.setLogin(userDto.getName());

        return jiraUser;
    }

    private char[] getCharArray() {
        char[] m = new char[26];
        for (int i = 0; i < 26; i++) {
            m[i] = (char) ('a' + i);
        }
        return m;
    }

    @Override
    public void loadData() {
        Set<JiraUserAutoDto> jiraUsers = new HashSet<>();

        for (char cha : getCharArray()) {
            HttpEntity<String> req = new HttpEntity<>(getHeaders());
            RestTemplate rest = new RestTemplate();
            JiraUserSchedulerDto[] userDtos = rest.exchange(USER_URL + cha, HttpMethod.GET, req, JiraUserSchedulerDto[].class).getBody();

            for (JiraUserSchedulerDto userDto : userDtos) {
                jiraUsers.add(fromDto(userDto));
            }
        }

        try {
            jiraUserService.saveAll(new ArrayList<>(jiraUsers));
        } catch (NoSuchEntityException e) {
            log.warning("Can't to add JiraUsers");
        }
    }

    @Override
    public JiraGroupsDto loadAllGroups() {
        return null;
    }

    @Override
    public ImportedJiraUsersDto loadAllUsersByGroupName(String name) {
        return null;
    }

}
