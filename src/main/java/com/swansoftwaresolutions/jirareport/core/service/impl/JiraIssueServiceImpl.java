package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.mapper.JiraIssueMapper;
import com.swansoftwaresolutions.jirareport.core.service.JiraIssueService;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraIssueRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.sheduller.dto.JiraIssueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Service
public class JiraIssueServiceImpl implements JiraIssueService {

    @Autowired
    JiraIssueRepository jiraIssueRepository;

    @Autowired
    JiraIssueMapper jiraIssueMapper;

    @Override
    public JiraIssueDto save(JiraIssueDto jiraIssueDto) {
        return jiraIssueMapper.toDto(jiraIssueRepository.add(jiraIssueMapper.fromDto(jiraIssueDto)));
    }

    @Override
    public JiraIssueDto update(JiraIssueDto jiraIssueDto) throws NoSuchEntityException{
        return jiraIssueMapper.toDto(jiraIssueRepository.update(jiraIssueMapper.fromDto(jiraIssueDto)));
    }

    @Override
    public List<JiraIssueDto> findAll() throws NoSuchEntityException {
        return jiraIssueMapper.toDtos(jiraIssueRepository.findAll());
    }

    @Override
    public List<JiraIssueDto> findBySprintId(long sprintId) throws NoSuchEntityException {
        return jiraIssueMapper.toDtos(jiraIssueRepository.findBySprintId(sprintId));
    }

    @Override
    public void delete(JiraIssueDto jiraIssue) throws NoSuchEntityException {
        jiraIssueRepository.delete(jiraIssueMapper.fromDto(jiraIssue));
    }

    @Override
    public List<JiraIssueDto> findBySprintIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return new ArrayList<>();
        }

        return jiraIssueMapper.toDtos(jiraIssueRepository.findBySprintIds(ids));

    }


}
