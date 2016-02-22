package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.Config;

import java.util.List;

public interface ConfigRepository {

    List<Config> getConfigData();

    void createConfig(final Config config);

    void updateConfig(final Config config);

    void deleteConfig(final Config config);

    void deleteConfig(final Long id);
}
