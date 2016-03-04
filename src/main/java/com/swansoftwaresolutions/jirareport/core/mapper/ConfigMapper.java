package com.swansoftwaresolutions.jirareport.core.mapper;

import com.swansoftwaresolutions.jirareport.core.entity.Config;
import com.swansoftwaresolutions.jirareport.rest.dto.ConfigDto;

/**
 * @author Vitaliy Hollovko
 */
public interface ConfigMapper {

    ConfigDto toDto(Config config);

    Config fromDto(ConfigDto configDto);
}
