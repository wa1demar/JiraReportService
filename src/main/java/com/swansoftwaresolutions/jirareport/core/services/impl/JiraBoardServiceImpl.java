package com.swansoftwaresolutions.jirareport.core.services.impl;

import com.swansoftwaresolutions.jirareport.core.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.core.repository.JiraBoardRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.services.JiraBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Service
public class JiraBoardServiceImpl implements JiraBoardService {

    @Autowired
    JiraBoardRepository jiraBoardRepository;

    @Override
    public JiraBoard save(JiraBoard jiraBoard) {
        return jiraBoardRepository.add(jiraBoard);
    }

    @Override
    public List<JiraBoard> findAll() {
        return jiraBoardRepository.findAll();
    }

    @Override
    public void delete(JiraBoard jiraBoard) throws NoSuchEntityException {
        jiraBoardRepository.delete(jiraBoard);
    }
}
