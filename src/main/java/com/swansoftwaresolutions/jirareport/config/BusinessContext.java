package com.swansoftwaresolutions.jirareport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Vladimir Martynyuk
 */
@Configuration
@ComponentScan(basePackages = {"com.swansoftwaresolutions.jirareport.core.service"})
public class BusinessContext {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}