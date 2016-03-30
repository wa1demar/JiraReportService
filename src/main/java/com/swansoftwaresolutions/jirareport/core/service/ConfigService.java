package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;

/**
 * @author Vladimir Martynyuk
 */
public interface ConfigService {

    ConfigDto retrieveConfig();

    ConfigDto update(ConfigDto configDto) throws NoSuchEntityException;
}
