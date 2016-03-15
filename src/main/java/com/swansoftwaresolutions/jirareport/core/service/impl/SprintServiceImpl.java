package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.sprint.NewSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint.SprintDtos;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintMapper;
import com.swansoftwaresolutions.jirareport.core.service.SprintService;
import com.swansoftwaresolutions.jirareport.domain.entity.Sprint;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class SprintServiceImpl implements SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private SprintMapper sprintMapper;


    @Override
    public SprintDto add(NewSprintDto sprintDto) {
        return sprintMapper.toDto(sprintRepository.add(sprintMapper.fromDto(sprintDto)));
    }

    @Override
    public SprintDto update(SprintDto sprintDto) {
        return sprintMapper.toDto(sprintRepository.update(sprintMapper.fromDto(sprintDto)));
    }

    @Override
    public SprintDtos findByReportId(long reportId) {
        List<Sprint> sprints = sprintRepository.findByReportId(reportId);

        return sprintMapper.toDto(sprints);
    }

    @Override
    public SprintDto delete(long sprintId) throws NoSuchEntityException {
        Sprint sprint = sprintRepository.delete(sprintId);
        return sprintMapper.toDto(sprint);
    }

    @Override
    public SprintDto findById(long sprintId) throws NoSuchEntityException {
        Sprint sprint = sprintRepository.findById(sprintId);
        return sprintMapper.toDto(sprint);
    }


}
