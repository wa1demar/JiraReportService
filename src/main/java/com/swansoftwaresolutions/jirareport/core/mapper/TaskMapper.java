package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.dto.task.TaskDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Task;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface TaskMapper {
    List<TaskDto> toDtos(List<Task> tasks);

    TaskDto toDto(Task task);
}
