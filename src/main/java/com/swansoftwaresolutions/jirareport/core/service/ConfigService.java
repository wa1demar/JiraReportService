package com.swansoftwaresolutions.jirareport.core.service;

import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;
import com.swansoftwaresolutions.jirareport.core.dto.ConfigDto;

import java.util.List;

/**
 * @author Vladimir Martynyuk
 */
public interface ConfigService {

    ConfigDto retrieveConfig();

    ConfigDto update(ConfigDto configDto) throws NoSuchEntityException;
}
