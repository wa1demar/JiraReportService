package com.swansoftwaresolutions.jirareport.config;

import com.swansoftwaresolutions.jirareport.sheduller.job.*;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */
@Configuration
@ComponentScan("com.swansoftwaresolutions.jirareport.sheduller.job")
@PropertySource("classpath:quartz.properties")
public class QuartzConfig {

    @Value("${job.small.expression}")
    private String jobSmallExpression;

    @Value("${job.medium.expression}")
    private String jobMediumExpression;

    @Value("${job.full.expression}")
    private String jobFullExpression;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean quartzScheduler() {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
        quartzScheduler.setConfigLocation(new ClassPathResource("quartz.properties"));

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        quartzScheduler.setJobFactory(jobFactory);

        Trigger[] triggers = {
                loadSmallTrigger().getObject(),
                loadMediumTrigger().getObject(),
                loadFullTrigger().getObject()
        };

        quartzScheduler.setTriggers(triggers);

        return quartzScheduler;
    }

    @Bean
    CronTriggerFactoryBean loadSmallTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(loadSmallJobDetail().getObject());
        cronTriggerFactoryBean.setCronExpression(jobSmallExpression);
        return cronTriggerFactoryBean;
    }

    @Bean
    JobDetailFactoryBean loadSmallJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SynchronizeSmallJob.class);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    CronTriggerFactoryBean loadMediumTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(loadMediumJobDetail().getObject());
        cronTriggerFactoryBean.setCronExpression(jobMediumExpression);
        return cronTriggerFactoryBean;
    }

    @Bean
    JobDetailFactoryBean loadMediumJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SynchronizeMediumJob.class);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    CronTriggerFactoryBean loadFullTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(loadFullJobDetail().getObject());
        cronTriggerFactoryBean.setCronExpression(jobFullExpression);
        return cronTriggerFactoryBean;
    }

    @Bean
    JobDetailFactoryBean loadFullJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SynchronizeFullJob.class);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

}
