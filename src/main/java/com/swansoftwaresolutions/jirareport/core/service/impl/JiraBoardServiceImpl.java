package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.entity.JiraBoard;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraBoardMapper;
import com.swansoftwaresolutions.jirareport.core.repository.JiraBoardRepository;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.service.JiraBoardService;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraBoardInfoDto;
import com.swansoftwaresolutions.jirareport.rest.dto.JiraBoardsDto;
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

    @Autowired
    JiraBoardMapper jiraBoardMapper;

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

    @Override
    public List<JiraBoardInfoDto> findAllBoardForInfo() {
        return jiraBoardMapper.toInfoDtos(jiraBoardRepository.findAll());
    }

    @Override
    public JiraBoardsDto retrieveAllBoards() {
        List<JiraBoard> boards = jiraBoardRepository.findAll();
        JiraBoardsDto boardsDto = new JiraBoardsDto(jiraBoardMapper.toDtos(boards));
        return boardsDto;
    }
}
