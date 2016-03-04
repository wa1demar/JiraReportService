package com.swansoftwaresolutions.jirareport.config;

import com.swansoftwaresolutions.jirareport.core.service.ConfigService;
import com.swansoftwaresolutions.jirareport.core.service.JiraUserService;
import com.swansoftwaresolutions.jirareport.core.service.ProjectService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Vladimir Martynyuk
 */
@Configuration
@ComponentScan(basePackages = {
        "com.swansoftwaresolutions.jirareport.core.mapper",
})
public class TestContext {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ConfigService configService() {
        return Mockito.mock(ConfigService.class);
    }

    @Bean
    public ProjectService projectService() {
        return Mockito.mock(ProjectService.class);
    }

    @Bean
    public JiraUserService jiraUserService() {
        return Mockito.mock(JiraUserService.class);
    }
}
