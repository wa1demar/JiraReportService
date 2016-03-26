package com.swansoftwaresolutions.jirareport.sheduller.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.domain.entity.Project;
import com.swansoftwaresolutions.jirareport.rest.client.AbstractRestClient;
import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */

@Component("projectClient")
public class ProjectDao extends AbstractRestClient implements RestClient {

    static Logger log = Logger.getLogger(ProjectDao.class.getName());

    @Autowired
    ProjectService projectService;

    @Override
    public void loadData() {
        final String uri = "https://swansoftwaresolutions.atlassian.net/rest/api/2/project.json";

        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        ProjectDto[] projectDtos = restTemplate.exchange(uri, HttpMethod.GET, request, ProjectDto[].class).getBody();

        insertDataToDataBase(projectDtos);
    }

    @Override
    public JiraGroupsDto loadAllGroups() {
        return null;
    }

    @Override
    public ImportedJiraUsersDto loadAllUsersByGroupName(String name) {
        return null;
    }


    private void insertDataToDataBase(ProjectDto[] projectDtos) {
        List<Project> projects = fromDtos(projectDtos);

        removeDublicateAndSave(projects);
    }

    private void removeDublicateAndSave(List<Project> projects) {
        projects.removeAll(projectService.findAll());

        for (Project project : projects) {
            projectService.save(project);
        }
    }

    private List<Project> fromDtos(ProjectDto[] projectDtos) {
       List<Project> projects = new ArrayList<>();
        for (ProjectDto projectDto : projectDtos){
            projects.add(fromDto(projectDto));
        }
        return projects;
    }

    private Project fromDto(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setKey(projectDto.getKey());
        project.setJiraId((long) projectDto.getId());

        return project;
    }

}
