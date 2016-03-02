package com.swansoftwaresolutions.jirareport.sheduller;

import com.swansoftwaresolutions.jirareport.core.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.services.JiraUserService;
import com.swansoftwaresolutions.jirareport.sheduller.dto.UserDto;
import com.swansoftwaresolutions.jirareport.sheduller.job.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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


    private void insertDataToDataBase(ArrayList<JiraUser> jiraUsers) {
        List<JiraUser> users = jiraUserService.findAll();

        removeDublicateAndSave(jiraUsers, users);
    }

    private void removeDublicateAndSave(ArrayList<JiraUser> jiraUsers, List<JiraUser> users) {
        List<JiraUser> jiraUsers1 = jiraUsers;
        jiraUsers.removeAll(new HashSet(users));

        for (JiraUser jiraUser : jiraUsers) {
            jiraUserService.save(jiraUser);
        }
    }

    private JiraUser fromDto(UserDto userDto) {
        JiraUser jiraUser = new JiraUser();
        jiraUser.setEmail(userDto.emailAddress);
        jiraUser.setFullName(userDto.displayName);
        jiraUser.setLogin(userDto.name);

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
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("-----------------------------------");
        System.out.println("-------Users Scheduler-----------");

        Set<JiraUser> jiraUsers = new HashSet<>();

        for (char cha : getCharArray()) {
            HttpEntity<String> req = new HttpEntity<>(getHeaders());
            RestTemplate rest = new RestTemplate();
            UserDto[] userDtos = rest.exchange(USER_URL + cha, HttpMethod.GET, req, UserDto[].class).getBody();

            for (UserDto userDto : userDtos) {
                jiraUsers.add(fromDto(userDto));
            }
        }
        System.out.println("   Users on Cloud : " + jiraUsers.size());

        insertDataToDataBase(new ArrayList<JiraUser>(jiraUsers));

        System.out.println("---User Scheduler Completed-----");
        System.out.println("-----------------------------------");
        System.out.println("");
    }
}
