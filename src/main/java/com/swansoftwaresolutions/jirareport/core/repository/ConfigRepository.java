package com.swansoftwaresolutions.jirareport.core.repository;

import com.swansoftwaresolutions.jirareport.core.entity.Config;

public interface ConfigRepository {

    Config getConfigData();

    void createConfig(final Config config);

    void updateConfig(final Config config);

    void deleteConfig(final Config config);

    void deleteConfig(final Long configId);
}
