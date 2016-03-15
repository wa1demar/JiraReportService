package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.ImportedSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintsDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
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

    @Autowired
    SprintMapper sprintMapper;

    @Override
    public SprintsDto retrieveByReportId(long reportId) {
        List<Sprint> sprints = sprintRepository.findByReportId(reportId);

        return sprintMapper.toDto(sprints);
    }

    @Override
    public SprintDto add(SprintDto sprint) {
        Sprint sprintDto = sprintMapper.fromDto(sprint);
        Sprint newSprint = sprintRepository.add(sprintDto);
        return sprintMapper.toDto(newSprint);
    }

    @Override
    public void add(ImportedSprintDto sprintDto) {
        Sprint sprint = sprintMapper.fromDto(sprintDto);
        sprintRepository.add(sprint);
    }

    @Override
    public List<ImportedSprintDto> findAll() {
        return sprintMapper.toDtos(sprintRepository.findAll());
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
