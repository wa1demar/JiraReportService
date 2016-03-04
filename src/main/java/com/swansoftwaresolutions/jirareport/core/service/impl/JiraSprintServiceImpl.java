package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.entity.Sprint;
import com.swansoftwaresolutions.jirareport.core.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.JiraSprintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Service
public class JiraSprintServiceImpl implements JiraSprintsService {

    @Autowired
    SprintRepository sprintRepository;

    @Override
    public Sprint save(Sprint sprint) {
        return sprintRepository.add(sprint);
    }

    @Override
    public List<Sprint> findAll() {
        return sprintRepository.findAll();
    }

    @Override
    public void delete(Sprint sprint) throws NoSuchEntityException {
        sprintRepository.delete(sprint);
    }

    @Override
    public void delete(Long sprintId) throws NoSuchEntityException {
        sprintRepository.delete(sprintId);
    }
}
