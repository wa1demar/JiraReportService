package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.Config;
import com.swansoftwaresolutions.jirareport.core.repository.exception.NoSuchEntityException;

import java.util.List;

public interface ConfigRepository {

    Config findFirst();

    Config findById(Long id);

    Config add(Config config);

    Config update(Config config);

    void delete(Config config) throws NoSuchEntityException;

    void delete(Long configId) throws NoSuchEntityException;
}
