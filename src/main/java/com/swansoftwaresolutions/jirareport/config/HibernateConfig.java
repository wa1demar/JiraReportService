package com.swansoftwaresolutions.jirareport.config;

import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * @author Vladimir Martynyuk
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
@ComponentScan({"com.swansoftwaresolutions.jirareport.domain.repository.impl"})
public class HibernateConfig {

    @Value("${database.driver}")
    private String databaseDriver;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database.user}")
    private String databaseUser;

    @Value("${database.password}")
    private String databasePassword;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateToDll;

    @Value("${hibernate.characterEncoding}")
    private String hibernateCharacterEncoding;

    @Value("${hibernate.format_sql}")
    private String hibernateFormatSql;

    @Value("${hibernate.c3p0.min_size}")
    private String c3poMinSize;

    @Value("${hibernate.c3p0.max_size}")
    private String c3poMaxSize;

    @Value("${hibernate.c3p0.timeout}")
    private String c3poTimeout;

    @Value("${hibernate.c3p0.max_statements}")
    private String c3poMaxStatements;

    @Value("${hibernate.c3p0.idle_test_period}")
    private String c3poTestPeriod;


    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.swansoftwaresolutions.jirareport.domain.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databaseDriver);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUser);
        dataSource.setPassword(databasePassword);
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", hibernateShowSql);
//        properties.put("hibernate.hbm2ddl.auto", hibernateToDll);
        properties.put("hibernate.characterEncoding", hibernateCharacterEncoding);
        properties.put("hibernate.format_sql", hibernateFormatSql);

        properties.put("hibernate.c3p0.min_size", c3poMinSize);
        properties.put("hibernate.c3p0.max_size", c3poMaxSize);
        properties.put("hibernate.c3p0.timeout", c3poTimeout);
        properties.put("hibernate.c3p0.max_statements", c3poMaxStatements);
        properties.put("hibernate.c3p0.idle_test_period", c3poTestPeriod);

        properties.put("hibernate.jdbc.batch_size", 20);
        properties.put("hibernate.jdbc.batch_versioned_data", true);
        properties.put("hibernate.order_updates", true);
        properties.put("hibernate.order_updates", true);
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

    @Bean
    SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:changelog.xml");
        liquibase.setContexts("test, production");

        return liquibase;
    }


}
