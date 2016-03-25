package com.swansoftwaresolutions.jirareport.core.dto.task;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public class TasksDto {
    private List<TaskDto> tasks;

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
}
