package com.swansoftwaresolutions.jirareport.sheduller.rest.client;

import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.JiraUserAutoDto;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraUserSchedulerDto;
import com.swansoftwaresolutions.jirareport.sheduller.job.RestClient;
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

@Component("jiraUserRestClient")
public class JiraUsersRestClient extends RestClientBase implements RestClient {

    static Logger log = Logger.getLogger(JiraUsersRestClient.class.getName());

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
        log.info("-----------------------------------");
        log.info("-------Users Scheduler-----------");

        Set<JiraUserAutoDto> jiraUsers = new HashSet<>();

        for (char cha : getCharArray()) {
            HttpEntity<String> req = new HttpEntity<>(getHeaders());
            RestTemplate rest = new RestTemplate();
            JiraUserSchedulerDto[] userDtos = rest.exchange(USER_URL + cha, HttpMethod.GET, req, JiraUserSchedulerDto[].class).getBody();

            for (JiraUserSchedulerDto userDto : userDtos) {
                jiraUsers.add(fromDto(userDto));
            }
        }
        log.info("   Users on Cloud : " + jiraUsers.size());

        try {
            jiraUserService.saveAll(new ArrayList<>(jiraUsers));
        } catch (NoSuchEntityException e) {
            log.warning("Can't to add JiraUsers");
        }

        log.info("---User Scheduler Completed-----");
        log.info("-----------------------------------");
        log.info("");
    }
}
