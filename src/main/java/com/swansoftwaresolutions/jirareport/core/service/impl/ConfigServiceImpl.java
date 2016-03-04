package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.mapper.ConfigMapper;
import com.swansoftwaresolutions.jirareport.core.repository.ConfigRepository;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.rest.dto.ConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vladimir Martynyuk
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigRepository configRepository;

    @Autowired
    ConfigMapper configMapper;

    @Override
    public ConfigDto retrieveConfig() {
        return configMapper.toDto(configRepository.findFirst());
    }

    @Override
    public ConfigDto update(ConfigDto configDto) {
        return configMapper.toDto(configRepository.update(configMapper.fromDto(configDto)));
    }
}
