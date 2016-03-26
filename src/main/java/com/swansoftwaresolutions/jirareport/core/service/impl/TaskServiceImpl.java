package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.task.TaskDto;
import com.swansoftwaresolutions.jirareport.core.dto.task.TasksDto;
import com.swansoftwaresolutions.jirareport.core.mapper.TaskMapper;
import com.swansoftwaresolutions.jirareport.core.service.TaskService;
import com.swansoftwaresolutions.jirareport.domain.entity.Task;
import com.swansoftwaresolutions.jirareport.domain.repository.TaskRepository;
import com.swansoftwaresolutions.jirareport.rest.service.GroupImporterService;
import com.swansoftwaresolutions.jirareport.rest.service.UserImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    GroupImporterService groupImporterService;

    @Autowired
    UserImporterService userImporterService;

    @Override
    public TasksDto getAll() {
        List<Task> tasks = taskRepository.findAll();

        TasksDto tasksDto = new TasksDto();
        tasksDto.setTasks(taskMapper.toDtos(tasks));
        return tasksDto;
    }

    @Override
    public TaskDto start(String name) {

        taskRepository.setStarted(name);
        switch (name) {
            case "groups":

                groupImporterService.importGroupsFromJira();
                break;

            case "users":

                userImporterService.importUsersFromJira();
                break;
        }

        Task task = taskRepository.setStopped(name);

        return taskMapper.toDto(task);
    }
}
