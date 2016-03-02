package com.swansoftwaresolutions.jirareport.sheduller;

import com.swansoftwaresolutions.jirareport.core.entity.Project;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.services.ProjectService;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ProjectDto;
import com.swansoftwaresolutions.jirareport.sheduller.job.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */

@Component("projectsRestClient")
public class ProjectsRestClient extends RestClientBase implements RestClient {

    static Logger log = Logger.getLogger(ProjectsRestClient.class.getName());

    @Autowired
    ProjectService projectService;

    public void loadData() {
        log.info("+++++++++++++++++++++++++++++++++++");
        log.info("-----------------------------------");
        log.info("-------Project Scheduler-----------");

        final String uri = "https://swansoftwaresolutions.atlassian.net/rest/api/2/project.json";

        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        ProjectDto[] projectDtos = restTemplate.exchange(uri, HttpMethod.GET, request, ProjectDto[].class).getBody();

        log.info("   Project on Cloud : " + projectDtos.length);
        insertDataToDataBase(projectDtos);

        log.info("---Project Scheduler Completed-----");
        log.info("-----------------------------------");
        log.info("");
    }

    private void insertDataToDataBase(ProjectDto[] projectDtos) {
        List<Project> projects = fromDtos(projectDtos);

        removeDublicateAndSave(projects, projectService.getAllProjects());
    }

    private void deleteOldProjects(List<Project> projects, List<Project> projectsDB) {
        projectsDB.removeAll(new HashSet(projects));
        for (Project project : projectsDB) {
            try {
                projectService.delete(project);
            } catch (NoSuchEntityException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeDublicateAndSave(List<Project> projects, List<Project> projectsDB) {
        List<Project> projectList = projects;
        projectList.removeAll(new HashSet(projectsDB));

        log.info("   Removed " + (projects.size() - projectList.size()) + " dublicates");
        for (Project project : projectList) {
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
        project.setName(projectDto.name);
        project.setKey(projectDto.key);
        project.setJiraId((long) projectDto.id);

        return project;
    }

}
