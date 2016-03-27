package com.swansoftwaresolutions.jirareport.sheduller.rest.client;

import com.swansoftwaresolutions.jirareport.core.dto.groups.JiraGroupsDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_project.ImportedProjectDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_users.ImportedJiraUsersDto;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
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
        final String uri = "https://swansoftwaresolutions.atlassian.net/rest/api/2/jiraProject.json";

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

    @Override
    public ImportedProjectDto[] loadAllProjects() {
        return new ImportedProjectDto[0];
    }


    private void insertDataToDataBase(ProjectDto[] projectDtos) {
        List<JiraProject> jiraProjects = fromDtos(projectDtos);

        removeDublicateAndSave(jiraProjects);
    }

    private void removeDublicateAndSave(List<JiraProject> jiraProjects) {
        jiraProjects.removeAll(projectService.findAll());

        for (JiraProject jiraProject : jiraProjects) {
            projectService.save(jiraProject);
        }
    }

    private List<JiraProject> fromDtos(ProjectDto[] projectDtos) {
       List<JiraProject> jiraProjects = new ArrayList<>();
        for (ProjectDto projectDto : projectDtos){
            jiraProjects.add(fromDto(projectDto));
        }
        return jiraProjects;
    }

    private JiraProject fromDto(ProjectDto projectDto) {
        JiraProject jiraProject = new JiraProject();
        jiraProject.setName(projectDto.getName());
        jiraProject.setKey(projectDto.getKey());

        return jiraProject;
    }

}
