package com.swansoftwaresolutions.jirareport.core.mapper.impl;

import com.swansoftwaresolutions.jirareport.domain.entity.Config;
import com.swansoftwaresolutions.jirareport.core.mapper.ConfigMapper;
import com.swansoftwaresolutions.jirareport.core.dto.ConfigDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Vladimir Martynyuk
 */
@Component
public class ConfigMapperImpl implements ConfigMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ConfigDto toDto(Config config) {
        return modelMapper.map(config, ConfigDto.class);
    }

    @Override
    public Config fromDto(ConfigDto configDto) {
        return modelMapper.map(configDto, Config.class);
    }
}
