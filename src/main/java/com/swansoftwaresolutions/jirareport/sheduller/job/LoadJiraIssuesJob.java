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
 * @author Vladimir Martynyuk
 */
@Component
public class LoadJiraIssuesJob implements Job {

    @Autowired
    @Qualifier("issueClient")
    RestClient jiraIssueRestDao;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LocalTime localTime = new LocalTime();

        System.out.println("JIRA ISSUES JOB - " + localTime.toString());

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        jiraIssueRestDao.loadData();
    }
}
