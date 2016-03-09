package com.swansoftwaresolutions.jirareport.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Vladimir Martynyuk
 */
@Configuration
@ComponentScan(basePackages = {
        "com.swansoftwaresolutions.jirareport.core.service",
        "com.swansoftwaresolutions.jirareport.core.mapper",
})
public class BusinessContext {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}