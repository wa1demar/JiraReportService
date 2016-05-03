package com.swansoftwaresolutions.jirareport.config;

import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author Vladimir Martynyuk
 */
public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.scan("com.swansoftwaresolutions.jirareport.config");

        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        FilterRegistration.Dynamic openSessionInViewFilter = container.addFilter("openSessionInViewFilter", OpenSessionInViewFilter.class);
        openSessionInViewFilter.setInitParameter("singleSession", "true");
        openSessionInViewFilter.addMappingForServletNames(null, true, "dispatcher");

        FilterRegistration.Dynamic characterEncodingFilter = container.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
        characterEncodingFilter.setInitParameter("singleSession", "true");
        characterEncodingFilter.addMappingForServletNames(null, true, "dispatcher");

        container.addListener(new ContextLoaderListener(appContext));

    }
}