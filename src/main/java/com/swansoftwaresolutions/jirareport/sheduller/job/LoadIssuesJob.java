package com.swansoftwaresolutions.jirareport.sheduller.job;

import org.joda.time.LocalTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Vitaliy Holovko
 */
public class LoadIssuesJob implements Job {

    LocalTime localTime = new LocalTime();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("ISSUE JOB - " + localTime.toString());
    }
}
