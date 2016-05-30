package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologiesDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyDto;
import com.swansoftwaresolutions.jirareport.core.mapper.TechnologyMapper;
import com.swansoftwaresolutions.jirareport.core.service.TechnologiesService;
import com.swansoftwaresolutions.jirareport.domain.entity.Technology;
import com.swansoftwaresolutions.jirareport.domain.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class TechnologiesServiceImpl implements TechnologiesService {

    @Autowired
    TechnologyRepository technologyRepository;

    @Autowired
    TechnologyMapper technologyMapper;

    @Override
    public TechnologiesDto findAll() {

        List<Technology> technologies = technologyRepository.findAll();

        TechnologiesDto technologiesDto = new TechnologiesDto();
        technologiesDto.setItems(technologyMapper.fromTechnologiesToTechnologiesDto(technologies));
        return technologiesDto;

    }

    @Override
    public TechnologyDto add(TechnologyDto technologyDto) {
        Technology technology = technologyRepository.add(technologyMapper.fromTechnologyDtoToTechnology(technologyDto));

        return technologyMapper.fromTechnologyToTechnologyDto(technology);
    }

    @Override
    public TechnologyDto delete(Long id) {
        Technology technology = technologyRepository.delete(id);
        return technologyMapper.fromTechnologyToTechnologyDto(technology);
    }

    @Override
    public TechnologyDto find(Long id) {
        Technology technology = technologyRepository.findById(id);
        return technologyMapper.fromTechnologyToTechnologyDto(technology);
    }

    @Override
    public TechnologyDto update(TechnologyDto technologyDto) {
        Technology technology = technologyRepository.update(technologyMapper.fromTechnologyDtoToTechnology(technologyDto));
        return technologyMapper.fromTechnologyToTechnologyDto(technology);
    }
}
