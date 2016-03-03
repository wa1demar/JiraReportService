package com.swansoftwaresolutions.jirareport.sheduller.rest.client;

import com.swansoftwaresolutions.jirareport.core.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.sheduller.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Vitaliy Holovko
 */
public class UsersRestClient extends RestClientBase {

    static Logger log = Logger.getLogger(UsersRestClient.class.getName());

    @Autowired
    JiraUserRepository jiraUserRepository;

    String USER_URL = "https://swansoftwaresolutions.atlassian.net/rest/api/2/user/search?username=%";


    public void getComments() {
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

    private void insertDataToDataBase(ArrayList<JiraUser> jiraUsers) {
        List<JiraUser> users = jiraUserRepository.findAll();

        removeDublicateAndSave(jiraUsers, users);
    }

    private void removeDublicateAndSave(ArrayList<JiraUser> jiraUsers, List<JiraUser> users) {
        List<JiraUser> jiraUsers1 = jiraUsers;
        jiraUsers.removeAll(new HashSet(users));

        System.out.println("   Removed " + (jiraUsers1.size()-jiraUsers.size()) + "dublicates");
        for (JiraUser jiraUser : jiraUsers) {
            jiraUserRepository.add(jiraUser);
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
}
