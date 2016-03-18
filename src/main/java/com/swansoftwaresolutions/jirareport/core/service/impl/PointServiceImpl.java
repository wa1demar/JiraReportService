package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.JiraPointDto;
import com.swansoftwaresolutions.jirareport.core.mapper.JiraPointMapper;
import com.swansoftwaresolutions.jirareport.core.service.PointService;
import com.swansoftwaresolutions.jirareport.domain.repository.PointRepository;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vitaliy Holovko
 */

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private JiraPointMapper jiraPointMapper;

    @Override
    public JiraPointDto add(JiraPointDto jiraPointDto) throws NoSuchEntityException {
        return jiraPointMapper.toDto(pointRepository.add(jiraPointMapper.fromDto(jiraPointDto)));
    }

    @Override
    public JiraPointDto update(JiraPointDto jiraPointDto) throws NoSuchEntityException {
        return jiraPointMapper.toDto(pointRepository.update(jiraPointMapper.fromDto(jiraPointDto)));
    }

    @Override
    public void delete(JiraPointDto jiraPointDto) throws NoSuchEntityException {
        pointRepository.delete(jiraPointMapper.fromDto(jiraPointDto));
    }

    @Override
    public void delete(Long id) throws NoSuchEntityException {
        pointRepository.delete(id);
    }

    @Override
    public List<JiraPointDto> findAll() throws NoSuchEntityException {
        return jiraPointMapper.toDtos(pointRepository.findAll());
    }

    @Override
    public JiraPointDto findById(Long pointId) throws NoSuchEntityException {
        return jiraPointMapper.toDto(pointRepository.findById(pointId));
    }
}
