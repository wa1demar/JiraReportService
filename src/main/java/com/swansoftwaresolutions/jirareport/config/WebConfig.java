package com.swansoftwaresolutions.jirareport.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author Vladimir Martynyuk
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.swansoftwaresolutions.jirareport.web.controller",
        "com.swansoftwaresolutions.jirareport.rest.controller",
        "com.swansoftwaresolutions.jirareport.rest.mapper",
        "com.swansoftwaresolutions.jirareport.rest.handler"
})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/WEB-INF/assets/");
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }
}
