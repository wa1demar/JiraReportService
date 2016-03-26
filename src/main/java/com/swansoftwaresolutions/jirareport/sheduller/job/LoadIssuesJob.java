package com.swansoftwaresolutions.jirareport.sheduller.job;

import com.swansoftwaresolutions.jirareport.rest.client.RestClient;
import org.joda.time.LocalTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * @author Vitaliy Holovko
 */
@Component
public class LoadIssuesJob implements Job {

    LocalTime localTime = new LocalTime();


    @Autowired
    @Qualifier("issueClient")
    RestClient issueRestDao;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("ISSUE JOB - " + localTime.toString());

        LocalTime localTime = new LocalTime();

        System.out.println("ISSUE JOB - " + localTime.toString());

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        issueRestDao.loadData();
    }
}
