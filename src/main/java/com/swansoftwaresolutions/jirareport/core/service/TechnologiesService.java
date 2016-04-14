package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologiesDto;
import com.swansoftwaresolutions.jirareport.core.dto.technologies.TechnologyDto;

/**
 * @author Vladimir Martynyuk
 */
public interface TechnologiesService {
    TechnologiesDto findAll();

    TechnologyDto add(TechnologyDto technologyDto);

    TechnologyDto delete(Long id);

    TechnologyDto find(Long id);

    TechnologyDto update(TechnologyDto technologyDto);
}
