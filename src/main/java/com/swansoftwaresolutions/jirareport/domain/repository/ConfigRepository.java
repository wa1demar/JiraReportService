package com.swansoftwaresolutions.jirareport.domain.repository;

import com.swansoftwaresolutions.jirareport.domain.entity.Config;
import com.swansoftwaresolutions.jirareport.domain.repository.exception.NoSuchEntityException;

public interface ConfigRepository {

    Config findFirst();

    Config findById(Long id);

    Config add(Config config);

    Config update(Config config);

    void delete(Config config) throws NoSuchEntityException;

    void delete(Long configId) throws NoSuchEntityException;
}
