package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.SprintIssue.SprintIssueListDto;
import com.swansoftwaresolutions.jirareport.core.dto.SprintIssueDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.NewSprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDeveloperDto;
import com.swansoftwaresolutions.jirareport.core.dto.sprint_developer.SprintDevelopersDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintDeveloperMapper;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintIssueMapper;
import com.swansoftwaresolutions.jirareport.core.service.SprintDeveloperService;
import com.swansoftwaresolutions.jirareport.core.service.SprintIssueService;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintDeveloper;
import com.swansoftwaresolutions.jirareport.domain.entity.SprintIssue;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintDeveloperRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintIssueRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.SprintRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitliy Holovko
 */
@Service
public class SprintIssueServiceImpl implements SprintIssueService {

    @Autowired
    SprintIssueMapper sprintIssueMapper;

    @Autowired
    SprintIssueRepository sprintIssueRepository;

    @Override
    public List<SprintIssueDto> findAll() throws NoSuchEntityException {
        return sprintIssueMapper.toDtos(sprintIssueRepository.findAll());
    }

    @Override
    public SprintIssueListDto findBySprintIdAndAsignee(Long sprintId, String assignee) {
        List<SprintIssueDto> list = sprintIssueMapper.toDtos(sprintIssueRepository.findBySprintIdAndAssignee(sprintId, assignee));
        SprintIssueListDto sprintIssueListDto = new SprintIssueListDto();
        sprintIssueListDto.setSprintIssueDtos(list);

        return sprintIssueListDto;
    }

    @Override
    public SprintIssueDto add(SprintIssueDto sprintIssueDto) {
        return sprintIssueMapper.toDto(sprintIssueRepository.add(sprintIssueMapper.fromDto(sprintIssueDto)));
    }
}
