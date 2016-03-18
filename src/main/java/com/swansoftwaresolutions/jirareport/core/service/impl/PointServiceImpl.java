package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.PointDto;
import com.swansoftwaresolutions.jirareport.core.mapper.PointMapper;
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
    private PointMapper pointMapper;

    @Override
    public PointDto add(PointDto pointDto) throws NoSuchEntityException {
        return pointMapper.toDto(pointRepository.add(pointMapper.fromDto(pointDto)));
    }

    @Override
    public PointDto update(PointDto pointDto) throws NoSuchEntityException {
        return pointMapper.toDto(pointRepository.update(pointMapper.fromDto(pointDto)));
    }

    @Override
    public void delete(PointDto pointDto) throws NoSuchEntityException {
        pointRepository.delete(pointMapper.fromDto(pointDto));
    }

    @Override
    public void delete(Long id) throws NoSuchEntityException {
        pointRepository.delete(id);
    }

    @Override
    public List<PointDto> findAll() throws NoSuchEntityException {
        return pointMapper.toDtos(pointRepository.findAll());
    }

    @Override
    public PointDto findById(Long pointId) throws NoSuchEntityException {
        return pointMapper.toDto(pointRepository.findById(pointId));
    }
}
