package com.swansoftwaresolutions.jirareport.core.mapper;


import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;
import com.swansoftwaresolutions.jirareport.domain.entity.Config;

/**
 * @author Vitaliy Hollovko
 */
public interface ConfigMapper {

    ConfigDto toDto(Config config);

    Config fromDto(ConfigDto configDto);
}
