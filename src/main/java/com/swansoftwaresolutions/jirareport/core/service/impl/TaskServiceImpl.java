package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.task.TaskDto;
import com.swansoftwaresolutions.jirareport.core.dto.task.TasksDto;
import com.swansoftwaresolutions.jirareport.core.mapper.TaskMapper;
import com.swansoftwaresolutions.jirareport.core.service.TaskService;
import com.swansoftwaresolutions.jirareport.domain.entity.Task;
import com.swansoftwaresolutions.jirareport.domain.repository.TaskRepository;
import com.swansoftwaresolutions.jirareport.rest.service.BoardImporterService;
import com.swansoftwaresolutions.jirareport.rest.service.GroupImporterService;
import com.swansoftwaresolutions.jirareport.rest.service.ProjectImporterService;
import com.swansoftwaresolutions.jirareport.rest.service.UserImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class TaskServiceImpl implements TaskService {

    private static final String GROUPS_TASK = "groups";
    private static final String USERS_TASK = "users";
    private static final String PROJECTS_TASK = "projects";
    private static final String BOARDS_TASK = "boards";
    private static final String SPRINTS_TASK = "sprints";
    private static final String ISSUES_TASK = "issues";

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    GroupImporterService groupImporterService;

    @Autowired
    UserImporterService userImporterService;

    @Autowired
    ProjectImporterService projectImporterService;

    @Autowired
    BoardImporterService boardImporterService;

    @Override
    public TasksDto getAll() {
        List<Task> tasks = taskRepository.findAll();

        TasksDto tasksDto = new TasksDto();
        tasksDto.setTasks(taskMapper.toDtos(tasks));
        return tasksDto;
    }

    @Override
    public TaskDto start(String name) {

        switch (name) {
            case USERS_TASK:
                startImportUsers();
                break;

            case GROUPS_TASK:
                startImportGroups();
                startImportUsers() ;
                break;

            case ISSUES_TASK:
                startImportIssues();
                break;

            case SPRINTS_TASK:
                startImportSprints();
                startImportIssues();
                break;

            case BOARDS_TASK:
                startImportBoards();
                startImportSprints();
                startImportIssues();
                break;

            case PROJECTS_TASK:
                startImportProjects();
                startImportBoards();
                startImportSprints();
                startImportIssues();
                break;

        }

        Task task = taskRepository.findByName(name);

        return taskMapper.toDto(task);
    }

    private void startImportGroups() {
        taskRepository.setStarted(GROUPS_TASK);
        groupImporterService.importGroupsFromJira();
        taskRepository.setStopped(GROUPS_TASK);
    }

    private void startImportUsers() {
        taskRepository.setStarted(USERS_TASK);
        userImporterService.importUsersFromJira();
        taskRepository.setStopped(USERS_TASK);
    }

    private void startImportProjects() {
        taskRepository.setStarted(PROJECTS_TASK);
        projectImporterService.importProjectsFromJira();
        taskRepository.setStopped(PROJECTS_TASK);
    }

    private void startImportBoards() {
        taskRepository.setStarted(BOARDS_TASK);
        boardImporterService.importBoardsFromJira();
        taskRepository.setStopped(BOARDS_TASK);
    }

    private void startImportSprints() {
        // TODO: should be implemented
    }

    private void startImportIssues() {
        // TODO: should be implemented
    }
}
