package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.JiraProject;
import com.swansoftwaresolutions.jirareport.domain.repository.JiraProjectRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.JiraProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Service
public class JiraProjectServiceImpl implements JiraProjectService {

    @Autowired
    JiraProjectRepository jiraProjectRepository;

    @Override
    public JiraProject save(JiraProject jiraProject) {
        return jiraProjectRepository.add(jiraProject);
    }

    @Override
    public JiraProject findByKey(String key) throws NoSuchEntityException {
        return jiraProjectRepository.findByKey(key);
    }

    @Override
    public List<JiraProject> findAll() {
        return jiraProjectRepository.findAll();
    }

    @Override
    public void delete(JiraProject jiraProject) throws NoSuchEntityException {
        jiraProjectRepository.delete(jiraProject);
    }
}
