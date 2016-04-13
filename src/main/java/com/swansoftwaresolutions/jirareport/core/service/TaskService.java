package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.task.TaskDto;
import com.swansoftwaresolutions.jirareport.core.dto.task.TasksDto;

/**
 * @author Vladimir Martynyuk
 */
public interface TaskService {
    TasksDto getAll();

    TaskDto start(String name);

    void startFullSynchronization();

    void startMediumSynchronization();

    void startSmallSynchronization();
}
