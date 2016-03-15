package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.ImportedJiraSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.JiraSprintDto;
import com.swansoftwaresolutions.jirareport.core.dto.jira_sprint.JiraSprintsDto;
import com.swansoftwaresolutions.jirareport.core.mapper.SprintMapper;
import com.swansoftwaresolutions.jirareport.domain.entity.JiraSprint;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraSprintRepository;
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
    JiraSprintRepository jiraSprintRepository;

    @Autowired
    SprintMapper sprintMapper;

    @Override
    public JiraSprintsDto retrieveByBoardId(long boardId) {
        List<JiraSprint> jiraSprints = jiraSprintRepository.findByBoardId(boardId);

        return sprintMapper.toDto(jiraSprints);
    }

    @Override
    public JiraSprintDto add(JiraSprintDto sprint) {
        JiraSprint jiraSprintDto = sprintMapper.fromDto(sprint);
        JiraSprint newJiraSprint = jiraSprintRepository.add(jiraSprintDto);
        return sprintMapper.toDto(newJiraSprint);
    }

    @Override
    public void add(ImportedJiraSprintDto sprintDto) {

        JiraSprint jiraSprint = sprintMapper.fromDto(sprintDto);
        jiraSprintRepository.add(jiraSprint);
    }

    @Override
    public List<ImportedJiraSprintDto> findAll() {
        return sprintMapper.toDtos(jiraSprintRepository.findAll());
    }

    @Override
    public void delete(JiraSprint jiraSprint) throws NoSuchEntityException {
        jiraSprintRepository.delete(jiraSprint.getId());
    }

    @Override
    public void delete(Long sprintId) throws NoSuchEntityException {
        jiraSprintRepository.delete(sprintId);
    }
}
