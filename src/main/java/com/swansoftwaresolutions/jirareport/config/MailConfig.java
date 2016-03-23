package com.swansoftwaresolutions.jirareport.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Vladimir Martynyuk
 */
@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {

    @Value("${email.host}")
    private String host;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${email.from}")
    private String from;

    @Value("${email.subject}")
    private String subject;

    @Value("${mail.transport.protocol}")
    private String transportProtocol;

    @Value("${mail.smtp.auth}")
    private String smtpAuth;

    @Value("${mail.smtp.starttls.enable}")
    private String smtpStartTls;

    @Value("${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        return javaMailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        return simpleMailMessage;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", transportProtocol);
        properties.setProperty("mail.smtp.auth", smtpAuth);
        properties.setProperty("mail.smtp.starttls.enable", smtpStartTls);
        properties.setProperty("mail.debug", debug);
        return properties;
    }

    @Bean
    public VelocityEngine velocityEngine() throws IOException {
        VelocityEngineFactoryBean factoryBean = new VelocityEngineFactoryBean();
        factoryBean.setResourceLoaderPath("classpath:velocity");

        return factoryBean.createVelocityEngine();
    }
}
