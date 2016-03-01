package com.swansoftwaresolutions.jirareport.config;

import com.swansoftwaresolutions.jirareport.sheduller.job.LoadProjectsJob;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author Vladimir Martynyuk
 */
@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean quartzScheduler() {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
        quartzScheduler.setConfigLocation(new ClassPathResource("quartz.properties"));

        Trigger[] triggers = {
                loadProjectTrigger().getObject()
        };

        quartzScheduler.setTriggers(triggers);

        return quartzScheduler;
    }

    @Bean
    CronTriggerFactoryBean loadProjectTrigger() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(loadProjectJobDetail().getObject());
        cronTriggerFactoryBean.setCronExpression("0 * * * * ?");
        return cronTriggerFactoryBean;
    }

    @Bean
    JobDetailFactoryBean loadProjectJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(LoadProjectsJob.class);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

}
