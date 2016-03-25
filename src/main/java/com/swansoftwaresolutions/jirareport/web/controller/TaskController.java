package com.swansoftwaresolutions.jirareport.web.controller;

import com.swansoftwaresolutions.jirareport.core.dto.task.TaskDto;
import com.swansoftwaresolutions.jirareport.core.dto.task.TasksDto;
import com.swansoftwaresolutions.jirareport.core.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vladimir Martynyuk
 */
@RestController
@RequestMapping("/rest/v1")
public class TaskController {

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<TasksDto> getAllTasks() {
        TasksDto tasks = taskService.getAll();

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @RequestMapping(value = "/tasks/{name}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TaskDto> startTask(@PathVariable("name") String name) {
        TaskDto task = taskService.start(name);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }


}
