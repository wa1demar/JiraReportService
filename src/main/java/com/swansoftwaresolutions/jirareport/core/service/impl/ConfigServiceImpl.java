package com.swansoftwaresolutions.jirareport.core.service.impl;

import com.swansoftwaresolutions.jirareport.core.Settings;
import com.swansoftwaresolutions.jirareport.core.mapper.ConfigMapper;
import com.swansoftwaresolutions.jirareport.domain.repository.ConfigRepository;
import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.core.dto.config.ConfigDto;
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
        if (Settings.isNotEmpty()) {
            return Settings.configDto();
        } else {
            ConfigDto configDto = configMapper.toDto(configRepository.findFirst());
            Settings.init(configDto.getId(),
                    configDto.getJiraUser(),
                    configDto.getJiraPass(),
                    configDto.getAgileDoneName(),
                    configDto.getJiraDevGroupName(),
                    configDto.getBugName(),
                    configDto.getNonWorkingDays(),
                    configDto.getDueDateIssueStatus()
                    );
            return configDto;
        }
    }

    @Override
    public ConfigDto update(ConfigDto configDto) {
        Settings.init(configDto.getId(),
                configDto.getJiraUser(),
                configDto.getJiraPass(),
                configDto.getAgileDoneName(),
                configDto.getJiraDevGroupName(),
                configDto.getBugName(),
                configDto.getNonWorkingDays(),
                configDto.getDueDateIssueStatus()
        );
        return configMapper.toDto(configRepository.update(configMapper.fromDto(configDto)));
    }
}
