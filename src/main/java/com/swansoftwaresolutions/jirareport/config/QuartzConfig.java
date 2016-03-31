package com.swansoftwaresolutions.jirareport.config;

import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import com.swansoftwaresolutions.jirareport.sheduller.rest.client.*;
import com.swansoftwaresolutions.jirareport.sheduller.job.*;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author Vladimir Martynyuk
 * @author Vitaliy Holovko
 */
@Configuration
@ComponentScan("com.swansoftwaresolutions.jirareport.sheduller.rest.client")
public class QuartzConfig {


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
//                loadProjectsTrigger().getObject(),
//                loadJiraUsersTrigger().getObject(),
//                loadJiraBoardsTrigger().getObject(),
                loadIssuesTrigger().getObject()
        };

        quartzScheduler.setTriggers(triggers);

        return quartzScheduler;
    }

    @Bean
    CronTriggerFactoryBean loadProjectsTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(loadProjectsJobDetail().getObject());
        cronTriggerFactoryBean.setCronExpression("12 * * * * ?");
        return cronTriggerFactoryBean;
    }

    @Bean
    JobDetailFactoryBean loadProjectsJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(LoadProjectsJob.class);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    CronTriggerFactoryBean loadIssuesTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(loadIssuesJobDetail().getObject());
        cronTriggerFactoryBean.setCronExpression("0 0/5 * * * ?");
//        cronTriggerFactoryBean.setCronExpression("12 0 * * * ?");
        return cronTriggerFactoryBean;
    }

    @Bean
    JobDetailFactoryBean loadIssuesJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(LoadIssuesJob.class);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

//    @Bean
//    CronTriggerFactoryBean loadJiraUsersTrigger() {
//        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
//        cronTriggerFactoryBean.setJobDetail(loadJiraUsersJobDetail().getObject());
//        cronTriggerFactoryBean.setCronExpression("12 0/2 * * * ?");
//        return cronTriggerFactoryBean;
//    }

//    @Bean
//    JobDetailFactoryBean loadJiraUsersJobDetail() {
//        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
//        jobDetailFactory.setJobClass(LoadUsersJob.class);
//        jobDetailFactory.setDurability(true);
//        return jobDetailFactory;
//    }

    @Bean
    CronTriggerFactoryBean loadJiraBoardsTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(loadJiraBoardsJobDetail().getObject());
        cronTriggerFactoryBean.setCronExpression("0 0/5 * * * ?");
        return cronTriggerFactoryBean;
    }

    @Bean
    JobDetailFactoryBean loadJiraBoardsJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(LoadJiraBoardsJob.class);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

}
