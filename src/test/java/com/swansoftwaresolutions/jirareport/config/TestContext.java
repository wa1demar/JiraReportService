package com.swansoftwaresolutions.jirareport.config;

import com.swansoftwaresolutions.jirareport.core.service.*;
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
@ComponentScan(basePackages = {
        "com.swansoftwaresolutions.jirareport.core.mapper",
})
public class TestContext {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
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

    @Bean
    public JiraSprintsService jiraSprintsService() {
        return Mockito.mock(JiraSprintsService.class);
    }

    @Bean
    public JiraBoardService jiraBoardService() {
        return Mockito.mock(JiraBoardService.class);
    }

    @Bean
    public ReportService reportService() {
        return Mockito.mock(ReportService.class);
    }

    @Bean
    public CommentService commentService() {
        return Mockito.mock(CommentService.class);
    }

    @Bean
    public SprintTeamService sprintTeamService() {
        return Mockito.mock(SprintTeamService.class);
    }
}
