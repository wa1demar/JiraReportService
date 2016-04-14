package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.dto.resourceboard.ResourceColumnDto;
import com.swansoftwaresolutions.jirareport.core.mapper.ResourceBordMapper;
import com.swansoftwaresolutions.jirareport.core.service.ResourceBordService;
import com.swansoftwaresolutions.jirareport.domain.entity.ResourceColumn;
import com.swansoftwaresolutions.jirareport.domain.repository.ResourceBordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class ResourceBordServiceImpl implements ResourceBordService {

    @Autowired
    ResourceBordRepository resourceBordRepository;

    @Autowired
    ResourceBordMapper resourceBordMapper;

    @Override
    public ResourceColumnDto add(ResourceColumnDto columnDto) {
        ResourceColumn resourceColumn = resourceBordRepository.add(resourceBordMapper.fromResourceColumnDtoToresourceColumn(columnDto));

        return resourceBordMapper.fromResourceColumnToresourceColumnDto(resourceColumn);
    }

    @Override
    public ResourceColumnDto update(ResourceColumnDto columnDto) {
        ResourceColumn resourceColumn = resourceBordRepository.update(resourceBordMapper.fromResourceColumnDtoToresourceColumn(columnDto));

        return resourceBordMapper.fromResourceColumnToresourceColumnDto(resourceColumn);
    }
}
