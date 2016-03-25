package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Task;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface TaskRepository {
    List<Task> findAll();

    Task findByName(String name);

    void setStarted(String name);

    Task setStoped(String name);
}
