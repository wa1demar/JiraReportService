package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.core.dto.task.TaskDto;
import com.swansoftwaresolutions.jirareport.core.mapper.TaskMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Task;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class TaskMapperImpl implements TaskMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<TaskDto> toDtos(List<Task> tasks) {
        Type targetistType = new TypeToken<List<TaskDto>>(){}.getType();
        return modelMapper.map(tasks, targetistType);
    }

    @Override
    public TaskDto toDto(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }
}
