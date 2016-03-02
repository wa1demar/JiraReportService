package com.swansoftwaresolutions.jirareport.sheduller;

import com.swansoftwaresolutions.jirareport.core.entity.Project;
import com.swansoftwaresolutions.jirareport.core.repository.ProjectRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.sheduller.dto.ProjectDto;
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
@Component
public class ProjectsRestClient extends RestClientBase {

    static Logger log = Logger.getLogger(ProjectsRestClient.class.getName());

    @Autowired
    ProjectRepository projectRepository;

    public void getComments() {
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("-----------------------------------");
        System.out.println("-------Project Scheduler-----------");
        final String uri = "https://swansoftwaresolutions.atlassian.net/rest/api/2/project.json";

        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        ProjectDto[] projectDtos = restTemplate.exchange(uri, HttpMethod.GET, request, ProjectDto[].class).getBody();

        System.out.println("   Project on Cloud : " + projectDtos.length);
        insertDataToDataBase(projectDtos);

        System.out.println("---Project Scheduler Completed-----");
        System.out.println("-----------------------------------");
        System.out.println("");
    }

    private void insertDataToDataBase(ProjectDto[] projectDtos) {
        List<Project> projectsDB = projectRepository.findAll();

        List<Project> projects = fromDtos(projectDtos);

        removeDublicateAndSave(projects, projectsDB);
//        deleteOldProjects(projects,projectsDB);
    }

    private void deleteOldProjects(List<Project> projects, List<Project> projectsDB) throws NoSuchEntityException {
        projectsDB.removeAll(new HashSet(projects));
        for (Project project : projectsDB) {
            projectRepository.delete(project);
        }
    }

    private void removeDublicateAndSave(List<Project> projects, List<Project> projectsDB) {
        List<Project> projectList = projects;
        projectList.removeAll(new HashSet(projectsDB));

        System.out.println("   Removed " + (projects.size() - projectList.size()) + "dublicates");
        for (Project project : projectList) {
            projectRepository.add(project);
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
