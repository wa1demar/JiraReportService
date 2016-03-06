package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.entity.JiraUser;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraUserMapper;
import com.swansoftwaresolutions.jirareport.core.repository.JiraUserRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Service
public class JiraUserServiceImpl implements JiraUserService {

    @Autowired
    JiraUserRepository jiraUserRepository;

    @Autowired
    JiraUserMapper jiraUserMapper;

    @Override
    public JiraUser save(JiraUser jiraUser) {
        return jiraUserRepository.add(jiraUser);
    }

    @Override
    public List<JiraUserDto> findAll() {
        return jiraUserMapper.toDtos(jiraUserRepository.findAll());
    }

    @Override
    public void delete(JiraUser jiraUser) throws NoSuchEntityException {
        jiraUserRepository.delete(jiraUser);
    }
}
