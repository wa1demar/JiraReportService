package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.task.TaskDto;
import com.swansoftwaresolutions.jirareport.core.dto.task.TasksDto;
import com.swansoftwaresolutions.jirareport.core.mapper.TaskMapper;
import com.swansoftwaresolutions.jirareport.core.service.TaskService;
import com.swansoftwaresolutions.jirareport.domain.entity.Task;
import com.swansoftwaresolutions.jirareport.domain.repository.TaskRepository;
import com.swansoftwaresolutions.jirareport.rest.service.*;
import com.swansoftwaresolutions.jirareport.rest.service.BoardImporterService;
import com.swansoftwaresolutions.jirareport.rest.service.GroupImporterService;
import com.swansoftwaresolutions.jirareport.rest.service.IssueImporterService;
import com.swansoftwaresolutions.jirareport.rest.service.ProjectImporterService;
import com.swansoftwaresolutions.jirareport.rest.service.UserImporterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class TaskServiceImpl implements TaskService {

    final static Logger logger = Logger.getLogger(TaskServiceImpl.class);

    private static final String GROUPS_TASK = "groups";
    private static final String USERS_TASK = "users";
    private static final String PROJECTS_TASK = "projects";
    private static final String BOARDS_TASK = "boards";
    private static final String SPRINTS_TASK = "sprints";
    private static final String ISSUES_TASK = "issues";
    private static final String DUEDATE_TASK = "duedate";

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

    @Autowired
    SprintImporterService sprintImporterService;

    @Autowired
    IssueImporterService issueImporterService;

    @Autowired
    DueDateImporterService dueDateImporterService;

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
                break;

            case DUEDATE_TASK:
                startImportDueDate();
                break;

            case ISSUES_TASK:
                startImportIssues();
                startImportDueDate();
                break;

            case SPRINTS_TASK:
                startImportSprints();
                break;

            case BOARDS_TASK:
                startImportBoards();
                break;

            case PROJECTS_TASK:
                startImportProjects();
                break;

        }

        Task task = taskRepository.findByName(name);

        return taskMapper.toDto(task);
    }

    @Override
    public void startFullSynchronization() {
        startImportGroups();
        startImportUsers();
        startImportProjects();
        startImportBoards();
        startImportSprints();
        startImportIssues();
        startImportDueDate();
    }

    @Override
    public void startMediumSynchronization() {
        startImportProjects();
        startImportBoards();
        startImportSprints();
        startImportIssues();
        startImportDueDate();
    }

    @Override
    public void startSmallSynchronization() {
        startImportIssues();
        startImportDueDate();
    }

    private void startImportGroups() {
        logger.info("Start task for groups at " + new Date());
        taskRepository.setStarted(GROUPS_TASK);
        groupImporterService.importGroupsFromJira();
        taskRepository.setStopped(GROUPS_TASK);
        logger.info("Stop task for groups at " + new Date());
    }

    private void startImportUsers() {
        logger.info("Start task for users at " + new Date());
        taskRepository.setStarted(USERS_TASK);
        userImporterService.importUsersFromJira();
        taskRepository.setStopped(USERS_TASK);
        logger.info("Stop task for users at " + new Date());
    }

    private void startImportProjects() {
        logger.info("Start task for projects at " + new Date());
        taskRepository.setStarted(PROJECTS_TASK);
        projectImporterService.importProjectsFromJira();
        taskRepository.setStopped(PROJECTS_TASK);
        logger.info("Stop task for projects at " + new Date());
    }

    private void startImportBoards() {
        logger.info("Start task for boards at " + new Date());
        taskRepository.setStarted(BOARDS_TASK);
        boardImporterService.importBoardsFromJira();
        taskRepository.setStopped(BOARDS_TASK);
        logger.info("Stop task for boards at " + new Date());
    }

    private void startImportSprints() {
        logger.info("Start task for sprints at " + new Date());
        taskRepository.setStarted(SPRINTS_TASK);
        sprintImporterService.loadSprintsFromJiraByBoard();
        sprintImporterService.addNewSprintsToExitingProjects();
        taskRepository.setStopped(SPRINTS_TASK);
        logger.info("Stop task for sprints at " + new Date());
    }

    private void startImportIssues() {
        logger.info("Start task for issues at " + new Date());
        taskRepository.setStarted(ISSUES_TASK);
        issueImporterService.importIssueFromJira();
        taskRepository.setStopped(ISSUES_TASK);
        logger.info("Stop task for issues at " + new Date());
    }

    private void startImportDueDate() {
        logger.info("Start task for dueDates at " + new Date());
        dueDateImporterService.importDueDate();
        logger.info("Stop task for dueDates at " + new Date());
    }
}
