package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.task.TaskDto;
import com.swansoftwaresolutions.jirareport.core.dto.task.TasksDto;
import com.swansoftwaresolutions.jirareport.core.mapper.TaskMapper;
import com.swansoftwaresolutions.jirareport.core.service.TaskService;
import com.swansoftwaresolutions.jirareport.domain.entity.Task;
import com.swansoftwaresolutions.jirareport.domain.repository.TaskRepository;
import com.swansoftwaresolutions.jirareport.rest.service.GroupImporterService;
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

    @Override
    public TasksDto getAll() {
        List<Task> tasks = taskRepository.findAll();

        TasksDto tasksDto = new TasksDto();
        tasksDto.setTasks(taskMapper.toDtos(tasks));
        return tasksDto;
    }

    @Override
    public TaskDto start(String name) {

        Task task = null;
        switch (name) {
            case "groups": {
                taskRepository.setStarted(name);
                groupImporterService.importGroupsFromJira();
                task = taskRepository.setStoped(name);
            }
        }

        return taskMapper.toDto(task);
    }
}
