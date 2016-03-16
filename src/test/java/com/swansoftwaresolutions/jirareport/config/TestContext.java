package com.swansoftwaresolutions.jirareport.config;

import com.swansoftwaresolutions.jirareport.core.service.*;
import com.swansoftwaresolutions.jirareport.domain.entity.User;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Vladimir Martynyuk
 */
@Configuration
public class TestContext {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
