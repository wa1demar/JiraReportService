package com.swansoftwaresolutions.jirareport.sheduller.job;

import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import org.joda.time.LocalTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * @author Vitaliy Holovko
 */
public class LoadUsersJob implements Job {

    @Autowired
    RestClient jiraUserRestDao;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LocalTime localTime = new LocalTime();

        System.out.println("USER JOB - " + localTime.toString());

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        jiraUserRestDao.loadData();
    }
}
